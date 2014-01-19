package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata._
import com.bca.lol.filesimporter.data._

class SaleConvertor {
  def convertSale ( sale: SaleData, units: List[ UnitData ], lots: List[LotData], options: List[OptionData], 
      conditions: List[ConditionData], comments: List[CommentData] ) = {
    val sale = new Sale
    val lots = List[Lot]()
    
    ( sale, lots )
  }
}