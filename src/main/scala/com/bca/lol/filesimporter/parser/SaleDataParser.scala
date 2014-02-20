package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.SaleData

class SaleDataParser extends FileParser {

  override def parseLine(line: String) = {
    val saleNumber = takeFirstField(line, 6)
    val saleCode = takeNextField(line, 3)
    val saleDescription = takeNextField(line, 25)
    val userGroupCode = takeNextField(line, 4)
    val saleDate = takeNextField(line, 7)
    val saleTime = takeNextField(line, 6)
    val saleVersion = takeNextField(line, 3)

    SaleData(saleNumber, saleCode, saleDescription, userGroupCode, saleDate, saleTime, saleVersion)
  }
}
