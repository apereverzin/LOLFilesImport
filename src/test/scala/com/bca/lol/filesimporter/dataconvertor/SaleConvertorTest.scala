package com.bca.lol.filesimporter.dataconvertor

import org.scalatest.{FlatSpec, BeforeAndAfter}
import com.bca.lol.filesimporter.BuildingMethods
import com.bca.lol.filesimporter.filedata._
import java.sql.Date
import java.util.Calendar

class SaleConvertorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val SaleNumber = "TEST74"
  val SaleCode = "TEST74"
  val SaleDescription = "Test Sale"
  val UserGroupCode = "TEST"
  val SaleDate = "1140131"
  val SaleTime = "110000"
  val SaleVersion = "1"

  var saleConvertor: SaleConvertor = _

  before {
    saleConvertor = new SaleConvertor
  }

  "SaleConvertor" should "convert valid sale" in {
    // given
    val sale = buildSaleData(SaleNumber, saleDescription = SaleDescription, saleCode = SaleCode, userGroupCode = UserGroupCode,
      saleDate = SaleDate, saleTime = SaleTime, saleVersion = SaleVersion)
    val importedData = new ImportedData(new ControlData(), List[SaleData](sale), List[UnitData](), List[LotData](), List[OptionData](), 
      List[ConditionData](), List[CommentData]())

    // when
    val convertedSale = saleConvertor.convertSale(importedData).get

    // then
    assert(convertedSale.name == SaleNumber)
    assert(convertedSale.description == SaleDescription)
    assert(convertedSale.auctionEnd.getTime == buildDateTime(14, 1, 31, 11, 0, 0).getTime)
    assert(convertedSale.auctionStart.getTime == buildDateTime(14, 1, 31, 11, 0, 0).getTime)
    assert(convertedSale.status == 0)
    assert(convertedSale.accessControl == 0)
    assert(convertedSale.locked == 0)
    assert(convertedSale.created != null)
    assert(convertedSale.catalogType == 0)
    assert(convertedSale.extended == "")
    assert(convertedSale.statusText == "")
    assert(convertedSale.reference == "")
    assert(convertedSale.lastModified != null)
    assert(convertedSale.ims == 0)
    assert(convertedSale.defaultLanguage == "")
    assert(convertedSale.documentLanguage == "")
  }
  
  private def buildDateTime(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Date = {
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
