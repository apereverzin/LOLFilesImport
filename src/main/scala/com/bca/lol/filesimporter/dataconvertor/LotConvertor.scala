package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.{UnitData, LotData, OptionData, ConditionData, CommentData}
import com.bca.lol.filesimporter.data.Lot
import scala.util.{Try, Success, Failure}
import com.bca.lol.filesimporter.data.LotElement
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.ListBuffer
import com.bca.lol.filesimporter.data.Option
import com.bca.lol.filesimporter.data.Comment
import com.bca.lol.filesimporter.data.Condition

class LotConvertor {
  var optionConvertor = new OptionConvertor
  var conditionConvertor = new ConditionConvertor
  var commentConvertor = new CommentConvertor
  
  def convertLot(res: ImportResult, unit: UnitData, lot: LotData, options: List[OptionData] = List[OptionData](),
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
        
        val convertedOptions = new ListBuffer[Option]
        val convertedConditions = new ListBuffer[Condition]
        val convertedComments = new ListBuffer[Comment]
        options.foreach(o => convertedOptions += (optionConvertor.convertOption(o).get))
        conditions.foreach(c => convertedConditions += (conditionConvertor.convertCondition(c).get))
        comments.foreach(c => convertedComments += (commentConvertor.convertComment(c).get))
        
        lot.options = convertedOptions.toList
        lot.conditions = convertedConditions.toList
        lot.comments = convertedComments.toList

        Success(lot)
      }
      case Failure(f) => Failure[Lot](f)
    }
  }
}
