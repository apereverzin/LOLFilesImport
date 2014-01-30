package com.bca.lol.filesimporter.validator

import org.scalatest.FlatSpec
import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.filedata.UnitData
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods

class UnitsValidatorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val unit1 = buildUnitData(unitSurrogate = "00000001", displaySequence = "001")
  val unit2 = buildUnitData(unitSurrogate = "00000002", displaySequence = "002")
  val lot1 = buildLotData(surrogateNumber = "00000001")
  val lot2 = buildLotData(surrogateNumber = "00000002")

  var unitsValidator: UnitsValidator = _

  before {
    unitsValidator = new UnitsValidator
  }

  "UnitsValidator" should "validate correct data" in {
    // given

    val units = List[UnitData](unit1, unit2)
    val lots = List[LotData](lot1, lot2)

    // when
    val res = unitsValidator.validateUnits(units, lots)

    // then
    assert(res hasNoErrors)
  }

  "UnitsValidator" should "fail validation if duplicated unit surrogate" in {
    // given
    val unitsValidator = new UnitsValidator

    val lots = List[LotData](lot1, lot2)
    val unit3 = buildUnitData(unitSurrogate = "00000001", displaySequence = "003")
    val units = List[UnitData](unit1, unit2, unit3)

    // when
    val res = unitsValidator.validateUnits(units, lots)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("Duplicated unit surrogate"))
  }

  "UnitsValidator" should "fail validation if duplicated unit display sequence" in {
    // given
    val unitsValidator = new UnitsValidator

    val lot3 = buildLotData(surrogateNumber = "00000003")
    val lots = List[LotData](lot1, lot2, lot3)
    val unit3 = buildUnitData(unitSurrogate = "00000003", displaySequence = "001")
    val units = List[UnitData](unit1, unit2, unit3)

    // when
    val res = unitsValidator.validateUnits(units, lots)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("Duplicated unit display sequence"))
  }

  "UnitsValidator" should "fail validation if wrong unit surrogate" in {
    // given
    val unitsValidator = new UnitsValidator

    val lots = List[LotData](lot1, lot2)
    val unit3 = buildUnitData(unitSurrogate = "00000003", displaySequence = "003")
    val units = List[UnitData](unit1, unit2, unit3)

    // when
    val res = unitsValidator.validateUnits(units, lots)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("Wrong unit surrogate"))
  }

  "UnitsValidator" should "fail validation if wrong lot surrogate number" in {
    // given
    val unitsValidator = new UnitsValidator

    val lot3 = buildLotData(surrogateNumber = "00000003")
    val lots = List[LotData](lot1, lot2, lot3)
    val units = List[UnitData](unit1, unit2)

    // when
    val res = unitsValidator.validateUnits(units, lots)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("Wrong lot surrogate number"))
  }
}
