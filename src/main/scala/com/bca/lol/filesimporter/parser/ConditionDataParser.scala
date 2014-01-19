package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.ConditionData

class ConditionDataParser extends FileLineParser {

  override def parseLine ( line: String ) = {
    val conditionData = new ConditionData
    
    conditionData.unitSurrogate = takeFirstField ( line, 9 )
    conditionData.inspCompCondDesc = takeNextField ( line, 30 )
    conditionData.status = takeNextField ( line, 20 )
    
    conditionData
  }
}
