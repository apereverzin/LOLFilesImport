package com.bca.lol.filesimporter

import com.bca.lol.filesimporter.filedata._

trait BuildingMethods {
  
  def buildSale ( saleNumber: String ) = {
    val sale = new SaleData
    
    sale.saleNumber = saleNumber

    sale
  }
  
  def buildUnit ( unitSurrogate: String, displaySequence: String ) = {
    val unit = new UnitData
    
    unit.unitSurrogate = unitSurrogate
    unit.displaySequence = displaySequence
    
    unit
  }

  def buildLot ( unitSurrogate: String ) : LotData = {
    buildLot ( "", unitSurrogate, "" )
  }
  
  def buildLot ( saleNumber: String, surrogateNumber: String, lotNumber: String ) = {
    val lot = new LotData
    
    lot.saleNumber = saleNumber
    lot.surrogateNumber = surrogateNumber
    lot.lotNumber = lotNumber
    
    lot
  }
  
  def buildOption ( unitSurrogate: String, displaySequence: String ) = {
    val option = new OptionData
    
    option.unitSurrogate = unitSurrogate
    option.displaySequence = displaySequence
    
    option
  }

  def buildCondition ( unitSurrogate: String, desc: String ) = {
    val condition = new ConditionData
    
    condition.unitSurrogate = unitSurrogate
    condition.inspCompCondDesc = desc
    
    condition
  }

  def buildComment ( unitSurrogate: String, sequenceNumber: String ) = {
    val comment = new CommentData
    
    comment.unitSurrogate = unitSurrogate
    comment.sequenceNumber = sequenceNumber
    
    comment
  }
}
