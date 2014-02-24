package com.bca.lol.filesimporter.parser

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter

class ConditionDataParserTest extends FlatSpec with BeforeAndAfter {
  var conditionDataParser: ConditionDataParser = _

  before {
    conditionDataParser = new ConditionDataParser
  }

  "ConditionDataParser" should "parse correct data" in {
    // given

    // when
    val conditionData = conditionDataParser.parseLine("000000003LITERATURE PACK               PRESENT             ")

    // then
    assert(conditionData.surrogateNumber == "000000003")
    assert(conditionData.inspCompCondDesc == "LITERATURE PACK")
    assert(conditionData.status == "PRESENT")
  }
}
