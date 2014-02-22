package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.{SaleData, UnitData, LotData, OptionData, ConditionData, CommentData}
import com.bca.lol.filesimporter.data.Sale
import com.bca.lol.filesimporter.directoryprocessor.ImportResult

class SaleConvertor {
  var lotsConvertor = new LotsConvertor
  
  def convertSale(res: ImportResult, sale: SaleData, units: List[UnitData], lots: List[LotData], options: List[OptionData], 
      conditions: List[ConditionData], comments: List[CommentData]) = {
    val convertedSale = new Sale

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
      
    convertedSale.lots = lotsConvertor.convertLots(res, units, lots, options, conditions, comments)

    convertedSale
  }
}
