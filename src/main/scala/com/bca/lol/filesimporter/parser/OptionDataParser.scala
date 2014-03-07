package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.OptionData

class OptionDataParser extends FileParser {

  override def parseLine(line: String) = {
    val optionData = new OptionData

    takeFirstField(line, 7)
    optionData.displaySequence = takeNextField(line, 3)
    optionData.description = takeNextField(line, 30)
    optionData.surrogateNumber = takeNextField(line, 9)

    optionData
  }
}
