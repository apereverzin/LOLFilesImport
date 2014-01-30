package com.bca.lol.filesimporter.validator

import org.scalatest.FlatSpec
import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods

class LotsValidatorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val SaleNumber = "CGV002"
  val sale = buildSaleData(SaleNumber)
  val lot1 = buildLotData(SaleNumber, "00000001", "001")

  var lotsValidator: LotsValidator = _

  before {
    lotsValidator = new LotsValidator
  }

  "LotsValidator" should "validate correct data" in {
    // given    
    val lot2 = buildLotData(SaleNumber, "00000002", "002")

    val lots = List[LotData](lot1, lot2)

    // when
    val res = lotsValidator.validateLots(sale, lots)

    // then
    assert(res hasNoErrors)
  }

  "LotsValidator" should "fail validation if wrong sale number" in {
    // given
    val lotsValidator = new LotsValidator

    val lot2 = buildLotData("CGV003", "00000002", "002")
    val lots = List[LotData](lot1, lot2)

    // when
    val res = lotsValidator.validateLots(sale, lots)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("sale number"))
  }

  "LotsValidator" should "fail validation if duplicated lot number" in {
    // given
    val lot2 = buildLotData(SaleNumber, "00000002", "002")
    val lot3 = buildLotData(SaleNumber, "00000003", "001")
    val lots = List[LotData](lot1, lot2, lot3)

    // when
    val res = lotsValidator.validateLots(sale, lots)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("lot number"))
  }

  "LotsValidator" should "fail validation if duplicated lot surrogate number" in {
    // given
    val lot2 = buildLotData(SaleNumber, "00000002", "002")
    val lot3 = buildLotData(SaleNumber, "00000001", "003")
    val lots = List[LotData](lot1, lot2, lot3)

    // when
    val res = lotsValidator.validateLots(sale, lots)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("lot surrogate number"))
  }
}
