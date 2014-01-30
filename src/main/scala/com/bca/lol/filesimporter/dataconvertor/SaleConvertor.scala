package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata._
import com.bca.lol.filesimporter.data._

class SaleConvertor {
  def convertSale ( sale: SaleData ) = {
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

    convertedSale
  }
}
