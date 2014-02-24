package com.bca.lol.filesimporter.parser

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter

class SaleDataParserTest extends FlatSpec with BeforeAndAfter {
  var saleDataParser: SaleDataParser = _

  before {
    saleDataParser = new SaleDataParser
  }

  "SaleDataParser" should "parse correct data" in {
    // given

    // when
    val saleData = saleDataParser.parseLine("SUNK74CGBTEST SALE54              TEST1140108220000001")

    // then
    assert(saleData.saleNumber == "SUNK74")
    assert(saleData.saleCode == "CGB")
    assert(saleData.saleDescription == "TEST SALE54")
    assert(saleData.userGroupCode == "TEST")
    assert(saleData.saleDate == "1140108")
    assert(saleData.saleTime == "220000")
    assert(saleData.saleVersion == "001")
  }
}
