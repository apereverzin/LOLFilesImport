package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set
import scala.collection.immutable.Set
import com.bca.lol.filesimporter.filedata.ConditionData

class ConditionsValidator {
  def validateConditions ( unitSurrogateNumbers: scala.collection.immutable.Set[String], conditions: List[ConditionData] ) = {
    val res = new ImportResult
    
    val unitSurrogateandConditions = scala.collection.mutable.Set[ ( String, String ) ]()
    
    for ( condition <- conditions ) {
      val unitSurrogate = condition.unitSurrogate
      if ( ! unitSurrogateNumbers.contains ( unitSurrogate ) ) res.addError ( buildConditionUnitSurrogateError ( unitSurrogate ) )
      if ( unitSurrogateandConditions.contains ( ( unitSurrogate, condition.inspCompCondDesc ) ) ) 
        res.addError ( buildUnitSurrogateAndConditionDuplicationError ( unitSurrogate, condition.inspCompCondDesc ) )
      else
        unitSurrogateandConditions += ( ( unitSurrogate, condition.inspCompCondDesc ) )
    }
    
    res
  }
  
  def buildConditionUnitSurrogateError ( unitSurrogate: String ) =
    String.format ( "Wrong unit surrogate %s for condition", unitSurrogate )
  
  def buildUnitSurrogateAndConditionDuplicationError ( unitSurrogate: String, inspCompCondDesc: String ) =
    String.format ( "Duplicate unit surrogate %s and condition", unitSurrogate, inspCompCondDesc )
}
