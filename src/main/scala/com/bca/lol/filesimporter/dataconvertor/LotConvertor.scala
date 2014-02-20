package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.UnitData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.filedata.OptionData
import com.bca.lol.filesimporter.filedata.ConditionData
import com.bca.lol.filesimporter.filedata.CommentData
import com.bca.lol.filesimporter.data.Lot
import scala.util.Try
import scala.util.Success
import scala.util.Failure

class LotConvertor {
  def convertLot(unit: UnitData, lot: LotData, options: List[OptionData] = List[OptionData](),
    conditions: List[ConditionData] = List[ConditionData](), comments: List[CommentData] = List[CommentData]()): Try[Lot] = {
    val displaySequence = Try[Int](Integer.parseInt(unit.displaySequence))

    displaySequence match {
      case Success(s) => {
        val lot = new Lot(displaySequence.get)

        lot.contextId = 0
        lot.lotId = 0
        lot.vendor = 0
        lot.title = ""
        lot.link = ""
        lot.image = ""
        lot.description = ""
        lot.bid = 0
        lot.bidCount = 0
        lot.reserve = 0
        lot.minimumBid = 0
        lot.price = 0
        lot.currency = ""
        lot.category = 0
        lot.template = 0
        lot.flags = 0
        lot.status = 0
        lot.quantity = 0
        lot.auctionEnd = 0L
        lot.auctionStart = 0
        lot.locked = 0
        lot.created = 0L
        lot.lotType = 0
        lot.displayId = ""
        lot.saleCode = ""
        lot.catalogId = 0

        Success(lot)
      }
      case Failure(f) => Failure[Lot](f)
    }
  }
}
