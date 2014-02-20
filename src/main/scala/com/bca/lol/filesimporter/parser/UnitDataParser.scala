package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.UnitData

class UnitDataParser extends FileParser {

  override def parseLine(line: String) = {
    val unitData = new UnitData

    takeFirstField(line, 7)
    unitData.unitSurrogate = takeNextField(line, 9)
    unitData.displaySequence = takeNextField(line, 3)
    unitData.registrationNumber = takeNextField(line, 10)
    unitData.fullMake = takeNextField(line, 15)
    unitData.fullModel = takeNextField(line, 15)
    unitData.engineSize = takeNextField(line, 2)
    unitData.fullDerivative = takeNextField(line, 30)
    unitData.bodyType = takeNextField(line, 15)
    unitData.numberOfDoors = takeNextField(line, 1)
    unitData.fuelType = takeNextField(line, 15)
    unitData.gearBoxType = takeNextField(line, 15)
    unitData.currentMileage = takeNextField(line, 6)
    unitData.mileageWarranted = takeNextField(line, 1)
    unitData.colour = takeNextField(line, 30)
    unitData.trim = takeNextField(line, 60)
    unitData.dateOf1stRegistration = takeNextField(line, 7)
    unitData.registrationYear = takeNextField(line, 4)
    unitData.registrationLetter = takeNextField(line, 1)
    unitData.taxExpiryDate = takeNextField(line, 7)
    unitData.motExpiryDate = takeNextField(line, 7)
    unitData.unitCondition = takeNextField(line, 25)
    unitData.sourceDescription = takeNextField(line, 30)
    unitData.serviceHistory = takeNextField(line, 25)
    unitData.lastServiceHistoryDate = takeNextField(line, 7)
    unitData.accidentDamage = takeNextField(line, 1)
    unitData.manufacturersWarranty = takeNextField(line, 25)
    unitData.logBookReceived = takeNextField(line, 1)
    unitData.numberOfOwners = takeNextField(line, 2)
    unitData.vatType = takeNextField(line, 15)
    unitData.inspectionDate = takeNextField(line, 7)

    unitData
  }
}
