package com.bca.lol.filesimporter.dataconvertor

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods
import com.bca.lol.filesimporter.filedata._

class SaleConvertorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val SaleNumber = "SUNK74"
  val UnitSurrogate1 = "00000001"
  val UnitSurrogate2 =  "00000002"  
  val DisplaySequence1 = "001"
  val DisplaySequence2 = "002"
  val SequenceNumber1 = "0001"
  val SequenceNumber2 = "0002"
  val ConditionDesc1 = "LITERATURE PACK"
  val ConditionDesc2 = "SPARE KEY"
    
  val unit1 = buildUnit ( UnitSurrogate1, DisplaySequence1 )
  val unit2 = buildUnit (UnitSurrogate2, DisplaySequence2 )
  val lot1 = buildLot ( UnitSurrogate1 )
  val lot2 = buildLot ( UnitSurrogate2 )
  val option1 = buildOption ( UnitSurrogate1, DisplaySequence1 )
  val option2 = buildOption ( UnitSurrogate2, DisplaySequence2 )
  val condition1 = buildCondition ( UnitSurrogate1, ConditionDesc1 )
  val condition2 = buildCondition ( UnitSurrogate2, ConditionDesc2 )  
  val comment1 = buildComment ( UnitSurrogate1, SequenceNumber1 )
  val comment2 = buildComment ( UnitSurrogate2, SequenceNumber2 )
  
  var saleConvertor : SaleConvertor = 
  _
  
  before {
    saleConvertor = new SaleConvertor
  }
  
  "SaleConvertor" should "should convert valid sale" in {
    // given
    val saleData = buildSale ( SaleNumber )
    val units = List[UnitData] ( unit1, unit2 )
    val lots = List[LotData] ( lot1, lot2 )
    val options = List[OptionData] ( option1, option2 )
    val conditions = List[ConditionData] ( condition1, condition2 )
    val comments = List[CommentData] ( comment1, comment2 )
    
    // when
    val saleAndLots = saleConvertor.convertSale ( saleData, units, lots, options, conditions, comments )
    
    // then
    assert ( saleAndLots._1.name == SaleNumber )
  }
}