package com.patres.converter.geo

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter




class GeoObject(private var line: String, var type: String, var dateTimeString: String) {

    companion object {
        val C1_POSITION_START = 2
        val C1_POSITION_END = 16
        val C2_POSITION_START = 18
        val C2_POSITION_END = 32
        val L1_POSITION_START = 33
        val L1_POSITION_END = 48
        val L2_POSITION_START = 50
        val L2_POSITION_END = 64
        val P2_POSITION_START = 66
        val P2_POSITION_END = 80
    }

    var c1: String = "00000000.0000"
    var c2: String = "00000000.0000"
    var l1: String = "00000000.0000"
    var l2: String = "00000000.0000"
    var p2: String = "00000000.0000"
    var dateTime: LocalDateTime

    init {
        c1 = getTextFromLine(C1_POSITION_START, C1_POSITION_END)
        c2 = getTextFromLine(C2_POSITION_START, C2_POSITION_END)
        l1 = getTextFromLine(L1_POSITION_START, L1_POSITION_END)
        l2 = getTextFromLine(L2_POSITION_START, L2_POSITION_END)
        p2 = getTextFromLine(P2_POSITION_START, P2_POSITION_END)

        val formatter = DateTimeFormatter.ofPattern("yy MM dd HH mm ss")
        dateTime = LocalDateTime.parse(dateTimeString.split(".")[0].replace("  ", " 0"), formatter)
    }

    private fun getTextFromLine(startPosition: Int, endPosition: Int): String {
        if (line.length >= endPosition) {
            val textFromLine = line.substring(startPosition, endPosition).replace(" ", "")
            if(textFromLine.isNotBlank()) {
                return textFromLine
            }
        }
        return "00000000.0000"
    }

}