package com.patres.converter.geo

import com.patres.converter.split
import java.io.File
import java.io.FileReader
import java.util.*

class Loader(private var file: File) {

    private val lines = ArrayList<GeoObject>()


    companion object {
        val measurementRegex = Regex(" [0-9]+ +[0-9]+ +[0-9]+ +[0-9]+ +[0-9]+ .*([GR]).*")
        val measurementNextLineRegex = Regex("^ {32}([G|R][0-9]{2})+")
        val grPosition = 32
        val startHeader = "END OF HEADER"
        val SEC_POSITION_START = 16
        val SEC_POSITION_END = 26
        val SEC = "0.0000000"

    }

    fun getLoadLines(readMinutes: Boolean): ArrayList<GeoObject> {
        val sc = Scanner(FileReader(file))
        var grPoints = mutableListOf<String>()
        var element = 0
        var canReadLine = false
        var readLine = false
        while (sc.hasNextLine()) {
            var line = sc.nextLine()
            if (line.contains(startHeader)) {
                canReadLine = true
                line = sc.nextLine()
            }
            if (canReadLine) {
                when {
                    line.matches(measurementRegex) -> {
                        readLine = (readMinutes && line.substring(SEC_POSITION_START, SEC_POSITION_END).trim() == SEC) || !readMinutes
                        element = 0
                        grPoints = getGrList(line)
                    }
                    line.matches(measurementNextLineRegex) -> grPoints.addAll(getGrList(line))
                    readLine -> {
                        lines.add(GeoObject(line, grPoints[element++]))
                    }
                }
            }
        }
        return lines
    }

    private fun getGrList(line: String): MutableList<String> {
        val grLine = line.substring(grPosition, line.length).trim()
        return grLine.split(3).toMutableList()
    }

}

