package com.bca.lol.filesimporter.parser

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter

class LotDataParserTest extends FlatSpec with BeforeAndAfter {
  var lotDataParser : LotDataParser = _
  
  before {
    lotDataParser = new LotDataParser
  }
  
  "LotDataParser" should "parse correct data" in {
    // given
    
    // when
    val lotData = lotDataParser.parseLine ( "000000002SUNK74CGV001                                                            00042000005300" )
    
    // then
    assert ( lotData.unitSurrogate == "000000002" )
    assert ( lotData.saleNumber == "SUNK74" )
    assert ( lotData.lotNumber == "CGV001" )
    assert ( lotData.description == "" )
    assert ( lotData.startingPrice == 4200 )
    assert ( lotData.reservePrice == 5300 )
  }
}
