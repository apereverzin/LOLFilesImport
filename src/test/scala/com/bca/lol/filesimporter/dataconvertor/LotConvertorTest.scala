package com.bca.lol.filesimporter.dataconvertor

import org.scalatest.{FlatSpec, BeforeAndAfter}
import com.bca.lol.filesimporter.BuildingMethods
import com.bca.lol.filesimporter.filedata._
import org.scalatest.mock._
import org.mockito.Mockito._
import com.bca.lol.filesimporter.data.Option

class LotConvertorTest extends FlatSpec with MockitoSugar with BeforeAndAfter with BuildingMethods {
  val UnitSurrogate1 = "00000001"
  val DisplaySequence1 = "001"
  val DisplaySequence2 = "a02"
  val SequenceNumber1 = "0001"
  val SequenceNumber2 = "0002"
  val ConditionDesc1 = "LITERATURE PACK"
  val ConditionDesc2 = "SPARE KEY"

  val lot = buildLotData(surrogateNumber = UnitSurrogate1)
  val option1 = buildOptionData(UnitSurrogate1, DisplaySequence1)
  val option2 = buildOptionData(UnitSurrogate1, DisplaySequence2)
  val condition1 = buildConditionData(UnitSurrogate1, ConditionDesc1)
  val condition2 = buildConditionData(UnitSurrogate1, ConditionDesc2)
  val comment1 = buildCommentData(UnitSurrogate1, SequenceNumber1)
  val comment2 = buildCommentData(UnitSurrogate1, SequenceNumber2)

  var lotConvertor: LotConvertor = _
  var optionConvertor: OptionConvertor = _
  var conditionConvertor: ConditionConvertor = _
  var commentConvertor: CommentConvertor = _

  before {
    lotConvertor = new LotConvertor
    optionConvertor = mock[OptionConvertor]
    conditionConvertor = mock[ConditionConvertor]
    commentConvertor = mock[CommentConvertor]
    
    lotConvertor.optionConvertor = optionConvertor
    lotConvertor.conditionConvertor = conditionConvertor
    lotConvertor.commentConvertor = commentConvertor
  }

  "LotConvertor" should "convert valid lot" in {
    // given
    val unit = buildUnitData(UnitSurrogate1, DisplaySequence1)
    val options = List[OptionData]()
    val conditions = List[ConditionData]()
    val comments = List[CommentData]()

    // when
    val convertedLot = lotConvertor.convertLot(unit, lot, options, conditions, comments)

    // then
    assert(convertedLot.isSuccess)
    assert(convertedLot.get.contextId == 0)
    assert(convertedLot.get.lotId == 0)
    assert(convertedLot.get.vendor == 0)
    assert(convertedLot.get.title == "")
    assert(convertedLot.get.link == "")
    assert(convertedLot.get.image == "")
    assert(convertedLot.get.description == "")
    assert(convertedLot.get.bid == 0)
    assert(convertedLot.get.bidCount == 0)
    assert(convertedLot.get.reserve == 0)
    assert(convertedLot.get.minimumBid == 0)
    assert(convertedLot.get.price == 0)
    assert(convertedLot.get.currency == "")
    assert(convertedLot.get.category == 0)
    assert(convertedLot.get.template == 0)
    assert(convertedLot.get.flags == 0)
    assert(convertedLot.get.status == 0)
    assert(convertedLot.get.quantity == 0)
    assert(convertedLot.get.auctionEnd == 0L)
    assert(convertedLot.get.auctionStart == 0)
    assert(convertedLot.get.locked == 0)
    assert(convertedLot.get.created == 0L)
    assert(convertedLot.get.lotType == 0)
    assert(convertedLot.get.displayId == "")
    assert(convertedLot.get.saleCode == "")
    assert(convertedLot.get.catalogId == 0)
  }

  "LotConvertor" should "fail to convert lot with incorrect display sequence" in {
    // given
    val unit = buildUnitData(UnitSurrogate1, DisplaySequence2)
    val options = List[OptionData]()
    val conditions = List[ConditionData]()
    val comments = List[CommentData]()

    // when
    val convertedLot = lotConvertor.convertLot(unit, lot, options, conditions, comments)

    // then
    assert(convertedLot.isFailure)
    assert(convertedLot.failed.get.getMessage().contains("For input string"))
  }
}
