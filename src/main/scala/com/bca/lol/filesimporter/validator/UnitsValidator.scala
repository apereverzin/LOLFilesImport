package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set
import com.bca.lol.filesimporter.filedata.UnitData

class UnitsValidator {
  def validateUnits(units: List[UnitData], lots: List[LotData]) = {
    val res = new ImportResult

    val lotSurrogateNumbers = Set[String]()
    val unitDisplaySequences = Set[String]()
    val unitSurrogateNumbers = Set[String]()

    lots.foreach(lotSurrogateNumbers += _.surrogateNumber)
    
    for (unit <- units) {
      if (unitDisplaySequences.contains(unit.displaySequence))
        res.addError(buildUnitDisplayDequenceDuplicationError(unit.displaySequence))
      else
        unitDisplaySequences += unit.displaySequence

      if (unitSurrogateNumbers.contains(unit.surrogateNumber))
        res.addError(buildUnitSurrogateDuplicationError(unit.surrogateNumber))
      else
        unitSurrogateNumbers += unit.surrogateNumber

      if (!lotSurrogateNumbers.contains(unit.surrogateNumber))
        res.addError(buildWrongUnitSurrogateError(unit.surrogateNumber))
    }

    for (lot <- lots)
      if (!unitSurrogateNumbers.contains(lot.surrogateNumber))
        res.addError(buildWrongLotSurrogateError(lot.surrogateNumber))

    res
  }

  def buildUnitDisplayDequenceDuplicationError(lotNumber: String) =
    s"Duplicated unit display sequence $lotNumber"

  def buildUnitSurrogateDuplicationError(surrogateNumber: String) =
    s"Duplicated unit surrogate $surrogateNumber"

  def buildWrongUnitSurrogateError(surrogateNumber: String) =
    s"Wrong unit surrogate $surrogateNumber"

  def buildWrongLotSurrogateError(surrogateNumber: String) =
    s"Wrong lot surrogate number $surrogateNumber"
}
