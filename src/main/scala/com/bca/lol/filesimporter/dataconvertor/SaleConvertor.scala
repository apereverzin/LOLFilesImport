package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.{ SaleData, UnitData, LotData, OptionData, ConditionData, CommentData }
import com.bca.lol.filesimporter.data.Sale
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.filedata.ImportedData
import scala.util.{ Try, Success, Failure }
import java.sql.Date
import java.util.Calendar

class SaleConvertor {
  var lotsConvertor = new LotsConvertor

  def convertSale(importedData: ImportedData): Try[Sale] = {
    try {
      val sale = new Sale

      val saleData = importedData.sales(0)
      sale.name = saleData.saleNumber
      sale.description = saleData.saleDescription      
      sale.auctionEnd = convertDateTime(saleData.saleDate + saleData.saleTime)
      sale.auctionStart = convertDateTime(saleData.saleDate + saleData.saleTime)
      sale.status = 0
      sale.accessControl = 0
      sale.locked = 0
      sale.created = new Date(System.currentTimeMillis())
      sale.catalogType = 0
      sale.extended = ""
      sale.statusText = ""
      sale.reference = ""
      sale.lastModified = new Date(System.currentTimeMillis())
      sale.ims = 0
      sale.defaultLanguage = ""
      sale.documentLanguage = ""

      sale.lots = lotsConvertor.convertLots(importedData.units, importedData.lots, importedData.options, importedData.conditions, importedData.comments)

      Success(sale)
    } catch {
      case e: Throwable => Failure(e)
    }
  }

  private def convertDateTime(dateTime: String): Date = {
    val year = Integer.parseInt(dateTime.substring(1, 3));
    val month = Integer.parseInt(dateTime.substring(3, 5));
    val day = Integer.parseInt(dateTime.substring(5, 7));
    val hour = Integer.parseInt(dateTime.substring(7, 9));
    val minute = Integer.parseInt(dateTime.substring(9, 11));
    val second = Integer.parseInt(dateTime.substring(11,13));

    val cal = Calendar.getInstance
    cal.set(Calendar.YEAR, 2000 + year)
    cal.set(Calendar.MONTH, month)
    cal.set(Calendar.DAY_OF_MONTH, day)
    cal.set(Calendar.HOUR_OF_DAY, hour)
    cal.set(Calendar.MINUTE, minute)
    cal.set(Calendar.SECOND, second)
    cal.set(Calendar.MILLISECOND, 0)
    
    new Date(cal.getTimeInMillis())
  }
}
