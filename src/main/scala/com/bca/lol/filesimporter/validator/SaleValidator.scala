package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set

class SaleValidator {
  def validateSale(sale: SaleData) = {
    val res = new ImportResult

    val saleNumber = sale.saleNumber

    res
  }

  def buildSaleNumberError(lot: LotData, saleNumber: String) =
    s"Wrong sale number for lot $lot.lotNumber: expected $lot.saleNumber, found $saleNumber"
}
