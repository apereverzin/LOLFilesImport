package com.bca.lol.filesimporter.parser

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter

class UnitDataParserTest extends FlatSpec with BeforeAndAfter {
  var unitDataParser : UnitDataParser = _

  before {
    unitDataParser = new UnitDataParser
  }
  
  "UnitDataParser" should "parse correct data" in {
    // given
        
    // when
    val unitData = unitDataParser.parseLine ( "0000002000000002002XK69 VXD  BMW            320D           25M SPART                       Saloon1        4Diesel         Automatic      199485YGREY                                                                                      10703300000Y00000001120616                                                       Yes                      1111208NExtended                 Y00Margin         0000000" )
    
    //then
    assert ( unitData.unitSurrogate == "0000002" )
    assert ( unitData.displaySequence == "002" )
    assert ( unitData.registrationNumber == "XK69 VXD" )
    assert ( unitData.fullMake == "BMW" )
    assert ( unitData.fullModel == "320D" )
    assert ( unitData.engineSize == "25" )
    assert ( unitData.fullDerivative == "M SPART" )
    assert ( unitData.bodyType == "Saloon1" )
    assert ( unitData.numberOfDoors == "4" )
    assert ( unitData.fuelType == "Diesel" )
    assert ( unitData.gearBoxType == "Automatic" )
    assert ( unitData.currentMileage == "199485" )
    assert ( unitData.mileageWarranted == "Y" )
    assert ( unitData.colour == "GREY" )
    assert ( unitData.trim == "" )
    assert ( unitData.dateOf1stRegistration == "1070330" )
    assert ( unitData.registrationYear == "0000" )
    assert ( unitData.registrationLetter == "Y" )
    assert ( unitData.taxExpiryDate == "0000000" )
    assert ( unitData.motExpiryDate == "1120616" )
    assert ( unitData.unitCondition == "" )
    assert ( unitData.sourceDescription == "" )
    assert ( unitData.serviceHistory == "Yes" )
    assert ( unitData.lastServiceHistoryDate == "1111208" )
    assert ( unitData.accidentDamage == "N" )
    assert ( unitData.manufacturersWarranty == "Extended" )
    assert ( unitData.logBookReceived == "Y" )
    assert ( unitData.numberOfOwners == "00" )
    assert ( unitData.vatType == "Margin" )
    assert ( unitData.inspectionDate == "0000000" )
  }
}
