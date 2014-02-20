package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set

class LotsValidator {
  def validateLots(sale: SaleData, lots: List[LotData]) = {
    val res = new ImportResult

    val saleNumber = sale.saleNumber
    val lotNumbers = Set[String]()
    val lotSurrogateNumbers = Set[String]()

    for (lot <- lots) {
      if (lot.saleNumber != saleNumber) res.addError(buildSaleNumberError(lot, saleNumber))

      if (lotNumbers.contains(lot.lotNumber))
        res.addError(buildLotNumberDuplicationError(lot.lotNumber))
      else
        lotNumbers += lot.lotNumber

      if (lotSurrogateNumbers.contains(lot.unitSurrogate))
        res.addError(buildLotSurrogateNumberDuplicationError(lot.unitSurrogate))
      else
        lotSurrogateNumbers += lot.unitSurrogate
    }

    res
  }

  def buildSaleNumberError(lot: LotData, saleNumber: String) =
    s"Wrong sale number for lot $lot.lotNumber: expected $lot.saleNumber, found $saleNumber"

  def buildLotNumberDuplicationError(lotNumber: String) =
    s"Duplicated lot number $lotNumber"

  def buildLotSurrogateNumberDuplicationError(surrogateNumber: String) =
    s"Duplicated lot surrogate number $surrogateNumber"
}
