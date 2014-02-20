package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

class LotsConvertor {
  def convertLots(units: List[UnitData], lots: List[LotData], options: List[OptionData], 
      conditions: List[ConditionData], comments: List[CommentData]) = {
    val surrogateNumbersAndUnits = buildSurrogateNumbersAndUnits(units)
    val surrogateNumbersAndLots = buildSurrogateNumbersAndLots(lots)
    val surrogateNumbersAndOptions = buildSurrogateNumbersAndOptions(options)
    val surrogateNumbersAndConditions = buildsurrogateNumbersAndConditions(conditions)
    val surrogateNumbersAndComments = buildsurrogateNumbersAndComments(comments)
    
  }
  
  private def buildSurrogateNumbersAndUnits(units: List[UnitData]) = {
    val surrogateNumbersAndUnits = HashMap[String, UnitData]()
    units.foreach(u => surrogateNumbersAndUnits.put(u.unitSurrogate, u))
    surrogateNumbersAndUnits
  }
  
  private def buildSurrogateNumbersAndLots(lots: List[LotData]) = {
    val surrogateNumbersAndLots = HashMap[String, LotData]()
    lots.foreach(l => surrogateNumbersAndLots.put(l.unitSurrogate, l))
    surrogateNumbersAndLots
  }
  
  private def buildSurrogateNumbersAndOptions(options: List[OptionData]) = {
    val surrogateNumbersAndOptions = buildMap(options)
  }
  
  private def buildsurrogateNumbersAndConditions(conditions: List[ConditionData]) = {
    val surrogateNumbersAndConditions = buildMap(conditions)
  }
  
  private def buildsurrogateNumbersAndComments(comments: List[CommentData]) = {
    val surrogateNumbersAndComments = buildMap(comments)
  }
  
  private def buildMap [T <: FileData] (objects: List[T]): Map[String, ListBuffer[T]] = {
    val snMap = new HashMap[String, ListBuffer[T]]
    for (o <- objects) {
      if (!snMap.contains(o.unitSurrogate)) snMap.put(o.unitSurrogate, new ListBuffer[T])
      snMap.get(o.unitSurrogate).get += o
    }
    snMap
  }
}
