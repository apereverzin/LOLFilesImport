package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.OptionData

class OptionDataParser extends FileLineParser {

  override def parseLine ( line: String ) = {
    val optionData = new OptionData
    
    optionData.unitSurrogate = takeFirstField ( line, 7 )
    optionData.displaySequence = takeNextField ( line, 3 )
    optionData.optionDescription = takeNextField ( line, 30 )
    
    optionData
  }
}
