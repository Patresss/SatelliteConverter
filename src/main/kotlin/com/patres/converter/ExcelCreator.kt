package com.patres.converter

import com.patres.converter.geo.GeoObject
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream


class ExcelCreator(geoList: ArrayList<GeoObject>) {

    private var groupedList: MutableMap<String, MutableList<GeoObject>> = geoList.groupByTo(mutableMapOf()) { it.type }.toSortedMap()
    private val workbook = XSSFWorkbook()

    @Throws(Exception::class)
    fun createExcel(file: File) {
        groupedList.forEach { type, geoList ->
            createSheet(type, geoList)
        }
        val fileOut = FileOutputStream(file)
        workbook.write(fileOut)
        workbook.close()
    }

    @Throws(Exception::class)
    private fun createSheet(type: String, geoList: MutableList<GeoObject>) {
        val sheet = workbook.createSheet(type)
        val rowhead = sheet.createRow(0)
        rowhead.createCell(0).setCellValue("C1")
        rowhead.createCell(1).setCellValue("C2")
        rowhead.createCell(2).setCellValue("L1")
        rowhead.createCell(3).setCellValue("L2")
        rowhead.createCell(4).setCellValue("P2")

        var rowNumber = 1
        geoList.forEach {
            val row = sheet.createRow(rowNumber++)
            row.createCell(0).setCellValue(it.c1)
            row.createCell(1).setCellValue(it.c2)
            row.createCell(2).setCellValue(it.l1)
            row.createCell(3).setCellValue(it.l2)
            row.createCell(4).setCellValue(it.p2)
        }
    }

}