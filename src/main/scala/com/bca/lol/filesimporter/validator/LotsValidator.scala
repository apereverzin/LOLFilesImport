package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set

class LotsValidator {
  def validateLots ( sale: SaleData, lots: List[LotData] ) = {
    val res = new ImportResult
    
    val saleNumber = sale.saleNumber
    val lotNumbers = Set[String]()
    val lotSurrogateNumbers = Set[String]()
    
    for ( lot <- lots ) {
      if ( lot.saleNumber != saleNumber ) res.addError ( buildSaleNumberError ( lot, saleNumber) )

      if ( lotNumbers.contains ( lot.lotNumber ) ) 
        res.addError ( buildLotNumberDuplicationError ( lot.lotNumber ) )
      else
        lotNumbers += lot.lotNumber
      
      if ( lotSurrogateNumbers.contains ( lot.surrogateNumber ) ) 
        res.addError ( buildLotSurrogateNumberDuplicationError ( lot.surrogateNumber ) )
      else
        lotSurrogateNumbers += lot.surrogateNumber
    }
    
    res
  }
  
  def buildSaleNumberError ( lot: LotData, saleNumber: String ) =
    String.format ( "Wrong sale number for lot %s: expected %s, found %s", lot.lotNumber, lot.saleNumber, saleNumber )
  
  def buildLotNumberDuplicationError ( lotNumber: String ) =
    String.format ( "Duplicated lot number %s", lotNumber )
  
  def buildLotSurrogateNumberDuplicationError ( surrogateNumber: String ) =
    String.format ( "Duplicated lot surrogate number %s", surrogateNumber )
}
