package com.bca.lol.filesimporter.validator

import java.io.File
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.filedata._

class FilesContentValidator {
  var unitsValidator = new UnitsValidator
  var lotsValidator = new LotsValidator
  var optionsValidator = new OptionsValidator
  var conditionsValidator = new ConditionsValidator
  var commentsValidator = new CommentsValidator

  def validateFilesContent(importedData: ImportedData): ImportResult = {

    var res = validateNumberOfEntries(importedData.control, importedData.sales, importedData.units, importedData.lots,
      importedData.options, importedData.conditions, importedData.comments)
    if (res hasErrors) return res

    res = unitsValidator.validateUnits(importedData.units, importedData.lots)
    if (res hasErrors) return res

    res = lotsValidator.validateLots(importedData.sales(0), importedData.lots)
    if (res hasErrors) return res

    val unitSurrogates = Set.empty ++ (importedData.units.map(_.surrogateNumber))

    res = optionsValidator.validateOptions(unitSurrogates, importedData.options)
    if (res hasErrors) return res

    res = conditionsValidator.validateConditions(unitSurrogates, importedData.conditions)
    if (res hasErrors) return res

    commentsValidator.validateComments(unitSurrogates, importedData.comments)
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

  def buildError(name: String, expected: Int, found: Int) = s"Wrong number of $name: expected $expected, found $found"
}
