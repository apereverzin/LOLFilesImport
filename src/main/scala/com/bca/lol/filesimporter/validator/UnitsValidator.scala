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

    lots.foreach(lotSurrogateNumbers += _.unitSurrogate)
    
    for (unit <- units) {
      if (unitDisplaySequences.contains(unit.displaySequence))
        res.addError(buildUnitDisplayDequenceDuplicationError(unit.displaySequence))
      else
        unitDisplaySequences += unit.displaySequence

      if (unitSurrogateNumbers.contains(unit.unitSurrogate))
        res.addError(buildUnitSurrogateDuplicationError(unit.unitSurrogate))
      else
        unitSurrogateNumbers += unit.unitSurrogate

      if (!lotSurrogateNumbers.contains(unit.unitSurrogate))
        res.addError(buildWrongUnitSurrogateError(unit.unitSurrogate))
    }

    for (lot <- lots)
      if (!unitSurrogateNumbers.contains(lot.unitSurrogate))
        res.addError(buildWrongLotSurrogateError(lot.unitSurrogate))

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
