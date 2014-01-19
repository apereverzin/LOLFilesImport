package com.bca.lol.filesimporter.filedata

sealed class UnitData extends FileData {
  var unitSurrogate: String = _
  var displaySequence: String = _
  var registrationNumber: String = _
  var fullMake: String = _
  var fullModel: String = _
  var engineSize: String = _
  var fullDerivative: String = _
  var bodyType: String = _
  var numberOfDoors: String = _
  var fuelType: String = _
  var gearBoxType: String = _
  var currentMileage: String = _
  var mileageWarranted: String = _
  var colour: String = _
  var trim: String = _
  var dateOf1stRegistration: String = _
  var registrationYear: String = _
  var registrationLetter: String = _
  var taxExpiryDate: String = _
  var motExpiryDate: String = _
  var unitCondition: String = _
  var sourceDescription: String = _
  var serviceHistory: String = _
  var lastServiceHistoryDate: String = _
  var accidentDamage: String = _
  var manufacturersWarranty: String = _
  var logBookReceived: String = _
  var numberOfOwners: String = _
  var vatType: String = _
  var inspectionDate: String = _
}
