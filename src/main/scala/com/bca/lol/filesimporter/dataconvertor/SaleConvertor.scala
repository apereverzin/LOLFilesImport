package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.{ SaleData, UnitData, LotData, OptionData, ConditionData, CommentData }
import com.bca.lol.filesimporter.data.Sale
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.filedata.ImportedData
import scala.util.{ Try, Success, Failure }

class SaleConvertor {
  var lotsConvertor = new LotsConvertor

  def convertSale(importedData: ImportedData): Try[Sale] = {
    try {
      val convertedSale = new Sale

      val sale = importedData.sales(0)
      convertedSale.name = sale.saleNumber
      convertedSale.description = sale.saleDescription
      convertedSale.auctionEnd = 0L
      convertedSale.auctionStart = 0L
      convertedSale.status = 0
      convertedSale.accessControl = 0
      convertedSale.locked = 0
      convertedSale.created = System.currentTimeMillis()
      convertedSale.catalogType = 0
      convertedSale.extended = ""
      convertedSale.statusText = ""
      convertedSale.reference = ""
      convertedSale.lastModified = System.currentTimeMillis()
      convertedSale.ims = 0
      convertedSale.defaultLanguage = ""
      convertedSale.documentLanguage = ""

      convertedSale.lots = lotsConvertor.convertLots(importedData.units, importedData.lots, importedData.options, importedData.conditions, importedData.comments)

      Success(convertedSale)
    } catch {
      case e: Throwable => Failure(e)
    }
  }
}
