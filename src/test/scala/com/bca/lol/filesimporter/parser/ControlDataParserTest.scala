package com.bca.lol.filesimporter.parser

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter

class ControlDataParserTest extends FlatSpec with BeforeAndAfter {
  var controlDataParser : ControlDataParser = _

  before {
    controlDataParser = new ControlDataParser
  }
  
  "ControlDataParser" should "parse correct data" in {
    // given
    val line1 = "SALE    1"
    val line2 = "UCOMMENT10"
    val line3 = "UNIT    10"
    val line4 = "CONDS   5"
    val line5 = "LOT     10"
    val line6 = "OPTION  53"
        
    // when
    val controlData = controlDataParser.parseLines ( Array[String] ( line1, line2, line3, line4, line5, line6) )
    
    // then
    assert ( controlData.salesNumber == 1 )
    assert ( controlData.commentsNumber == 10 )
    assert ( controlData.unitsNumber == 10 )
    assert ( controlData.conditionsNumber == 5 )
    assert ( controlData.lotsNumber == 10 )
    assert ( controlData.optionsNumber ==53 )
  }
}
