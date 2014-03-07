package com.bca.lol.filesimporter.parser

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter

class OptionDataParserTest extends FlatSpec with BeforeAndAfter {
  var optionDataParser: OptionDataParser = _

  before {
    optionDataParser = new OptionDataParser
  }

  "OptionDataParser" should "parse correct data" in {
    // given

    // when
    val optionData = optionDataParser.parseLine("0000002001SH - 6(2bmw)stps to 148k 12/11000000002")

    // then
    assert(optionData.surrogateNumber == "000000002")
    assert(optionData.displaySequence == "001")
    assert(optionData.description == "SH - 6(2bmw)stps to 148k 12/11")
  }
}
