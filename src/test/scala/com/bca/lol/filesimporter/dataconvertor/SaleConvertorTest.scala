package com.bca.lol.filesimporter.dataconvertor

import org.scalatest.{FlatSpec, BeforeAndAfter}
import com.bca.lol.filesimporter.BuildingMethods
import com.bca.lol.filesimporter.filedata._
import com.bca.lol.filesimporter.directoryprocessor.ImportResult

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
    val res = new ImportResult

    // when
    val convertedSale = saleConvertor.convertSale(res, sale, List[UnitData](), List[LotData](), List[OptionData](), 
      List[ConditionData](), List[CommentData]())

    // then
    assert(convertedSale.name == SaleNumber)
    assert(convertedSale.description == SaleDescription)
    assert(convertedSale.auctionEnd == 0L)
    assert(convertedSale.auctionStart == 0L)
    assert(convertedSale.status == 0)
    assert(convertedSale.accessControl == 0)
    assert(convertedSale.locked == 0)
    assert(convertedSale.created > 0L)
    assert(convertedSale.catalogType == 0)
    assert(convertedSale.extended == "")
    assert(convertedSale.statusText == "")
    assert(convertedSale.reference == "")
    assert(convertedSale.lastModified > 0L)
    assert(convertedSale.ims == 0)
    assert(convertedSale.defaultLanguage == "")
    assert(convertedSale.documentLanguage == "")
  }
}
