package com.bca.lol.filesimporter.validator

import java.io.File
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.filedata.ControlData
import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.UnitData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.filedata.OptionData
import com.bca.lol.filesimporter.filedata.ConditionData
import com.bca.lol.filesimporter.filedata.CommentData

class FilesContentValidator {
  var unitsValidator = new UnitsValidator
  var lotsValidator = new LotsValidator
  var optionsValidator = new OptionsValidator
  var conditionsValidator = new ConditionsValidator
  var commentsValidator = new CommentsValidator

  def validateFilesContent(importedData: (ControlData, List[SaleData], List[UnitData], List[LotData], List[OptionData], List[ConditionData], List[CommentData])): ImportResult = {

    var res = validateNumberOfEntries(importedData._1, importedData._2, importedData._3, importedData._4,
      importedData._5, importedData._6, importedData._7)
    if (res.hasNoErrors) return res

    res = unitsValidator.validateUnits(importedData._3, importedData._4)
    if (res.hasNoErrors) return res

    res = lotsValidator.validateLots(importedData._2(0), importedData._4)
    if (res.hasNoErrors) return res

    val unitSurrogates = Set.empty ++ (importedData._3.map(_.unitSurrogate))

    res = optionsValidator.validateOptions(unitSurrogates, importedData._5)
    if (res.hasNoErrors) return res

    res = conditionsValidator.validateConditions(unitSurrogates, importedData._6)
    if (res.hasNoErrors) return res

    res = commentsValidator.validateComments(unitSurrogates, importedData._7)

    res
  }

  private def validateNumberOfEntries(control: ControlData, sales: List[SaleData], units: List[UnitData], lots: List[LotData],
    options: List[OptionData], conditions: List[ConditionData], comments: List[CommentData]) = {

    val res = new ImportResult

    if (control.salesNumber != sales.size || sales.size != 1) res.addError(buildError("sales", 1, sales.size))
    if (control.unitsNumber != units.size) res.addError(buildError("units", control.unitsNumber, units.size))
    if (control.lotsNumber != lots.size) res.addError(buildError("lots", control.lotsNumber, lots.size))
    if (control.optionsNumber != options.size) res.addError(buildError("options", control.optionsNumber, options.size))
    if (control.conditionsNumber != conditions.size) res.addError(buildError("conditions", control.conditionsNumber, conditions.size))
    if (control.commentsNumber != comments.size) res.addError(buildError("comments", control.commentsNumber, comments.size))

    res
  }

  def buildError(name: String, expected: Int, found: Int) = String.format("Wrong number of %s: expected %s, found %s",
    name, expected.toString, found.toString)
}
