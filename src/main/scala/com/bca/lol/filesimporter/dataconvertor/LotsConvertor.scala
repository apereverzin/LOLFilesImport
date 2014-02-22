package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.{UnitData, LotData, OptionData, ConditionData, CommentData}
import scala.collection.mutable.{Map, HashMap, ListBuffer}
import com.bca.lol.filesimporter.data.Lot
import com.bca.lol.filesimporter.filedata.FileData
import com.bca.lol.filesimporter.directoryprocessor.ImportResult

class LotsConvertor {
  private val lotConvertor = new LotConvertor
  
  def convertLots(res: ImportResult, units: List[UnitData], lots: List[LotData], options: List[OptionData], 
      conditions: List[ConditionData], comments: List[CommentData]) = {
    val surrogateNumbersAndUnits = buildSurrogateNumbersAndUnits(units)
    val surrogateNumbersAndOptions = buildSurrogateNumbersAndOptions(options)
    val surrogateNumbersAndConditions = buildSurrogateNumbersAndConditions(conditions)
    val surrogateNumbersAndComments = buildsurrogateNumbersAndComments(comments)
    
    val convertedLots = new ListBuffer[Lot]
    
    for(lot <- lots) {
      lotConvertor.convertLot(res, surrogateNumbersAndUnits.get(lot.surrogateNumber).get, 
          lot, 
          surrogateNumbersAndOptions.get(lot.surrogateNumber).getOrElse(new ListBuffer[OptionData]).toList, 
          surrogateNumbersAndConditions.get(lot.surrogateNumber).getOrElse(new ListBuffer[ConditionData]).toList, 
          surrogateNumbersAndComments.get(lot.surrogateNumber).getOrElse(new ListBuffer[CommentData]).toList)
    }
    
    convertedLots.toList
  }
  
  private def buildSurrogateNumbersAndUnits(units: List[UnitData]) = {
    val surrogateNumbersAndUnits = HashMap[String, UnitData]()
    units.foreach(u => surrogateNumbersAndUnits.put(u.surrogateNumber, u))
    surrogateNumbersAndUnits
  }
  
  private def buildSurrogateNumbersAndOptions(options: List[OptionData]) = {
    buildMap(options)
  }
  
  private def buildSurrogateNumbersAndConditions(conditions: List[ConditionData]) = {
    buildMap(conditions)
  }
  
  private def buildsurrogateNumbersAndComments(comments: List[CommentData]) = {
    buildMap(comments)
  }
  
  private def buildMap [T <: FileData] (objects: List[T]): Map[String, ListBuffer[T]] = {
    val snMap = new HashMap[String, ListBuffer[T]]
    for (o <- objects) {
      if (!snMap.contains(o.surrogateNumber)) snMap.put(o.surrogateNumber, new ListBuffer[T])
      snMap.get(o.surrogateNumber).get += o
    }
    snMap
  }
}
