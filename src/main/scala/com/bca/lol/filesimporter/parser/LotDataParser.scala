package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.LotData

class LotDataParser extends FileParser {

  override def parseLine(line: String) = {
    val lotData = new LotData

    lotData.surrogateNumber = takeFirstField(line, 9)
    lotData.saleNumber = takeNextField(line, 6)
    lotData.lotNumber = takeNextField(line, 6)
    lotData.description = takeNextField(line, 60)
    lotData.startingPrice = Integer.parseInt(takeNextField(line, 7))
    lotData.reservePrice = Integer.parseInt(takeNextField(line, 7))

    lotData
  }
}
