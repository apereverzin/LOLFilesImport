package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set
import com.bca.lol.filesimporter.filedata.UnitData

class UnitsValidator {
  def validateUnits ( units: List[UnitData], lots: List[LotData] ) = {
    val res = new ImportResult
    
    val lotSurrogateNumbers = Set[String]()
    val unitDisplaySequences = Set[String]()
    val unitSurrogateNumbers = Set[String]()
    
    for ( lot <- lots ) lotSurrogateNumbers += lot.surrogateNumber
    
    for ( unit <- units ) {
      if ( unitDisplaySequences.contains ( unit.displaySequence ) ) 
        res.addError ( buildUnitDisplayDequenceDuplicationError ( unit.displaySequence ) )
      else
        unitDisplaySequences += unit.displaySequence
      
      if ( unitSurrogateNumbers.contains ( unit.unitSurrogate ) ) 
        res.addError ( buildUnitSurrogateDuplicationError ( unit.unitSurrogate ) )
      else
        unitSurrogateNumbers += unit.unitSurrogate
      
      if ( ! lotSurrogateNumbers.contains ( unit.unitSurrogate ) ) 
        res.addError ( buildWrongUnitSurrogateError ( unit.unitSurrogate ) )
    }
    
    for ( lot <- lots ) 
      if ( ! unitSurrogateNumbers.contains ( lot.surrogateNumber ) ) 
        res.addError ( buildWrongLotSurrogateError ( lot.surrogateNumber ) )

    res
  }
  
  def buildUnitDisplayDequenceDuplicationError ( lotNumber: String ) =
    String.format ( "Duplicated unit display sequence %s", lotNumber )
  
  def buildUnitSurrogateDuplicationError ( surrogateNumber: String ) =
    String.format ( "Duplicated unit surrogate %s", surrogateNumber )
  
  def buildWrongUnitSurrogateError ( surrogateNumber: String ) =
    String.format ( "Wrong unit surrogate %s", surrogateNumber )
  
  def buildWrongLotSurrogateError ( surrogateNumber: String ) =
    String.format ( "Wrong lot surrogate number %s", surrogateNumber )
}
