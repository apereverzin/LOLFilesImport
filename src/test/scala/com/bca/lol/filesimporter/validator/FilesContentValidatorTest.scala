package com.bca.lol.filesimporter.validator

import org.scalatest._
import com.bca.lol.filesimporter.filedata._
import org.scalatest.junit.AssertionsForJUnit
import org.scalatest.mock._
import org.mockito.Mockito._
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.BuildingMethods

class FilesContentValidatorTest extends FlatSpec with AssertionsForJUnit with MockitoSugar with BeforeAndAfter with BuildingMethods {
  val UnitSurrogate1 = "0000001";

  var filesContentValidator: FilesContentValidator = _
  var unitsValidator: UnitsValidator = _
  var lotsValidator: LotsValidator = _
  var optionsValidator: OptionsValidator = _
  var conditionsValidator: ConditionsValidator = _
  var commentsValidator: CommentsValidator = _

  before {
    filesContentValidator = new FilesContentValidator
    unitsValidator = mock[UnitsValidator]
    lotsValidator = mock[LotsValidator]
    optionsValidator = mock[OptionsValidator]
    conditionsValidator = mock[ConditionsValidator]
    commentsValidator = mock[CommentsValidator]

    filesContentValidator.unitsValidator = unitsValidator
    filesContentValidator.lotsValidator = lotsValidator
    filesContentValidator.optionsValidator = optionsValidator
    filesContentValidator.conditionsValidator = conditionsValidator
    filesContentValidator.commentsValidator = commentsValidator
  }

  "FilesContentValidator" should "validate correct data" in {
    // given
    val sale = buildSaleData()
    val lot = LotData()
    val lots = List[LotData](lot)
    val unit = buildUnitData(unitSurrogate = UnitSurrogate1)
    val units = List[UnitData](unit)
    val unitSurrogates = Set[String](UnitSurrogate1)
    val option = OptionData()
    val options = List[OptionData](option)
    val condition = ConditionData()
    val conditions = List[ConditionData](condition)
    val comment = buildCommentData()
    val comments = List[CommentData](comment)
    val control = new ControlData(1, 1, 1, 1, 1, 1)
    val importedData = new ImportedData(control, List[SaleData](sale), units, lots, options, conditions, comments)

    when(lotsValidator.validateLots(sale, lots)).thenReturn(ImportResult())
    when(unitsValidator.validateUnits(units, lots)).thenReturn(ImportResult())
    when(optionsValidator.validateOptions(unitSurrogates, options)).thenReturn(ImportResult())
    when(conditionsValidator.validateConditions(unitSurrogates, conditions)).thenReturn(ImportResult())
    when(commentsValidator.validateComments(unitSurrogates, comments)).thenReturn(ImportResult())

    // when
    val res = filesContentValidator.validateFilesContent(importedData)

    // then
    assert(res hasNoErrors)
  }

  "FilesContentValidator" should "fail to validate wrong number of sales" in {
    // given
    val filesContentValidator = new FilesContentValidator
    val control = new ControlData(1, 1, 1, 1, 1, 1)
    val importedData = new ImportedData(control, List[SaleData](SaleData(), SaleData()),
      List[UnitData](UnitData()),
      List[LotData](LotData()), List[OptionData](OptionData()), List[ConditionData](ConditionData()),
      List[CommentData](CommentData()))

    // when
    val res = filesContentValidator.validateFilesContent(importedData)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("sales"))
  }

  "FilesContentValidator" should "fail to validate wrong number of units" in {
    // given
    val filesContentValidator = new FilesContentValidator
    val control = new ControlData(1, 2, 1, 1, 1, 1)
    val importedData = new ImportedData(control, List[SaleData](SaleData()), List[UnitData](UnitData()),
      List[LotData](LotData()), List[OptionData](OptionData()), List[ConditionData](ConditionData()),
      List[CommentData](CommentData()))

    // when
    val res = filesContentValidator.validateFilesContent(importedData)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("units"))
  }

  "FilesContentValidator" should "fail to validate wrong number of lots" in {
    // given
    val filesContentValidator = new FilesContentValidator
    val control = new ControlData(1, 1, 2, 1, 1, 1)
    val importedData = new ImportedData(control, List[SaleData](SaleData()), List[UnitData](UnitData()),
      List[LotData](LotData()), List[OptionData](OptionData()), List[ConditionData](ConditionData()),
      List[CommentData](CommentData()))

    // when
    val res = filesContentValidator.validateFilesContent(importedData)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("lots"))
  }

  "FilesContentValidator" should "fail to validate wrong number of options" in {
    // given
    val filesContentValidator = new FilesContentValidator
    val control = new ControlData(1, 1, 1, 2, 1, 1)
    val importedData = new ImportedData(control, List[SaleData](SaleData()), List[UnitData](UnitData()),
      List[LotData](LotData()), List[OptionData](OptionData()), List[ConditionData](ConditionData()),
      List[CommentData](new CommentData))

    // when
    val res = filesContentValidator.validateFilesContent(importedData)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("options"))
  }

  "FilesContentValidator" should "fail to validate wrong number of conditions" in {
    // given
    val filesContentValidator = new FilesContentValidator
    val control = new ControlData(1, 1, 1, 1, 2, 1)
    val importedData = new ImportedData(control, List[SaleData](SaleData()), List[UnitData](UnitData()),
      List[LotData](LotData()), List[OptionData](OptionData()), List[ConditionData](ConditionData()),
      List[CommentData](CommentData()))

    // when
    val res = filesContentValidator.validateFilesContent(importedData)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("conditions"))
  }

  "FilesContentValidator" should "fail to validate wrong number of comments" in {
    // given
    val filesContentValidator = new FilesContentValidator
    val control = new ControlData(1, 1, 1, 1, 1, 2)
    val importedData = new ImportedData(control, List[SaleData](SaleData()), List[UnitData](UnitData()),
      List[LotData](LotData()), List[OptionData](OptionData()), List[ConditionData](ConditionData()),
      List[CommentData]())

    // when
    val res = filesContentValidator.validateFilesContent(importedData)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("comments"))
  }

  def buildUnit(surrogateNumber: String) = {
    val unit = UnitData()
    unit.surrogateNumber = surrogateNumber

    unit
  }
}
