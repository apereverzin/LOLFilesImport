package com.bca.lol.filesimporter.validator

import org.scalatest.FlatSpec
import com.bca.lol.filesimporter.filedata.ConditionData
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods

class ConditionsValidatorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val unitSurrogateNumbers = Set[String] ( "00000001", "00000002", "00000003" )
  val condition1 = buildCondition ( "00000001", "LITERATURE PACK" )
  val condition2 = buildCondition ( "00000002", "SPARE KEY" )

  var conditonsValidator : ConditionsValidator = _
  
  before {
    conditonsValidator = new ConditionsValidator
  }
  
  "ConditionsValidator" should "validate correct data" in {
    // given
    val conditions = List[ConditionData] ( condition1, condition2 )
    
    // when
    val res = conditonsValidator.validateConditions(unitSurrogateNumbers, conditions)
    
    // then
    assert ( !res.hasErrors )
  }

  "ConditionsValidator" should "fail validation if wrong unit surrogate" in {
    // given
    val condition3 = buildCondition ( "00000004", "SPARE KEY" )
    val conditions = List[ConditionData] ( condition1, condition2, condition3 )
    
    // when
    val res = conditonsValidator.validateConditions(unitSurrogateNumbers, conditions)
    
    // then
    assert ( res.hasErrors )
    assert ( res.getErrors.size == 1 )
    assert ( res.getErrors ( 0 ).contains( "Wrong unit surrogate" ) )
  }

  "ConditionsValidator" should "fail validation if duplicate condition" in {
    // given
    val condition3 = buildCondition("00000002", "SPARE KEY")
    val condition4 = buildCondition("00000001", "SPARE KEY")
    val conditions = List[ConditionData] ( condition1, condition2, condition3, condition4 )
    
    // when
    val res = conditonsValidator.validateConditions(unitSurrogateNumbers, conditions)
    
    // then
    assert ( res.hasErrors )
    assert ( res.getErrors.size == 1 )
    assert ( res.getErrors ( 0 ).contains( "Duplicate unit surrogate" ) )
  }
}
