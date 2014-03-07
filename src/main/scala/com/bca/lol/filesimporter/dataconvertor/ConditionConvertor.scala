package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.ConditionData
import com.bca.lol.filesimporter.data.LotCondition
import scala.util.{Try, Success}

class ConditionConvertor {
  def convertCondition (conditionData: ConditionData): Try[LotCondition] = {
    println(s"convertCondition ${conditionData.description}")
    val condition = new LotCondition

    condition.displaySequence = 0
    condition.description = conditionData.description
    condition.status = conditionData.status

    Success(condition)
  }
}
