package com.bca.lol.filesimporter

import com.bca.lol.filesimporter.filedata._

trait BuildingMethods {

  def buildSaleData(saleNumber: String = "", saleCode: String = "", saleDescription: String = "", userGroupCode: String = "",
    saleDate: String = "", saleTime: String = "", saleVersion: String = "") = {
    SaleData(saleNumber,
      saleCode,
      saleDescription,
      userGroupCode,
      saleDate,
      saleTime,
      saleVersion)
  }

  def buildUnitData(unitSurrogate: String = "", displaySequence: String = "") = {
    val unitData = new UnitData

    unitData.unitSurrogate = unitSurrogate
    unitData.displaySequence = displaySequence

    unitData
  }

  def buildLotData(saleNumber: String = "", surrogateNumber: String = "", lotNumber: String = "") = {
    val lotData = new LotData

    lotData.saleNumber = saleNumber
    lotData.surrogateNumber = surrogateNumber
    lotData.lotNumber = lotNumber

    lotData
  }

  def buildOptionData(unitSurrogate: String = "", displaySequence: String = "", optionDescription: String = "") = {
    val optionData = new OptionData

    optionData.unitSurrogate = unitSurrogate
    optionData.displaySequence = displaySequence
    optionData.optionDescription = optionDescription

    optionData
  }

  def buildConditionData(unitSurrogate: String = "", desc: String = "", status: String = "") = {
    val conditionData = new ConditionData

    conditionData.unitSurrogate = unitSurrogate
    conditionData.inspCompCondDesc = desc
    conditionData.status = status

    conditionData
  }

  def buildCommentData(unitSurrogate: String = "", commentType: String = "", sequenceNumber: String = "", comment: String = "") = {
    CommentData(unitSurrogate, commentType, sequenceNumber, comment)
  }
}
