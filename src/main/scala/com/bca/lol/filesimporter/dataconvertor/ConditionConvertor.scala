package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.ConditionData
import com.bca.lol.filesimporter.data.Condition

class ConditionConvertor {
  def convertCondition (conditionData: ConditionData) = {
    val condition = new Condition

    condition.inspCompCondDesc = conditionData.inspCompCondDesc
    condition.status = conditionData.status

    condition
  }
}
