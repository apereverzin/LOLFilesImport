package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.ConditionData
import com.bca.lol.filesimporter.data.Condition
import scala.util.{Try, Success}

class ConditionConvertor {
  def convertCondition (conditionData: ConditionData): Try[Condition] = {
    val condition = new Condition

    condition.inspCompCondDesc = conditionData.inspCompCondDesc
    condition.status = conditionData.status

    Success(condition)
  }
}
