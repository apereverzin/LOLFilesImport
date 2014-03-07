package com.bca.lol.filesimporter.dataconvertor

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods
import com.bca.lol.filesimporter.filedata._

class ConditionConvertorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val UnitSurrogate1 = "00000001"
  val Status1 = "Status1"
  val Status2 = "Status2"
  val ConditionDescription1 = "Descr 1"
  val ConditionDescription2 = "Descr 2"

  var conditionConvertor: ConditionConvertor = _

  before {
    conditionConvertor = new ConditionConvertor
  }

  "ConditionConvertor" should "convert valid condition" in {
    // given
    val condition = buildConditionData(desc = ConditionDescription1, status = Status1)

    // when
    val convertedCondition = conditionConvertor.convertCondition(condition)

    // then
    assert(convertedCondition.isSuccess)
    assert(convertedCondition.get.description == ConditionDescription1)
    assert(convertedCondition.get.status == Status1)
  }
}
