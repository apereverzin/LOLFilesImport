package com.bca.lol.filesimporter.validator

import org.scalatest.FlatSpec
import com.bca.lol.filesimporter.filedata.OptionData
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods

class OptionsValidatorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val unitSurrogateNumbers = Set[String] ( "00000001", "00000002", "00000003" )
  val option1 = buildOption ( "00000001", "001" )

  var optionsValidator: OptionsValidator = _

  before {
    optionsValidator = new OptionsValidator
  }
  
  "OptionsValidator" should "validate correct data" in {
    // given
    
    val option2 = buildOption ( "00000002", "001" )
    val options = List[OptionData] ( option1, option2 )
    
    // when
    val res = optionsValidator.validateOptions(unitSurrogateNumbers, options)
    
    // then
    assert ( !res.hasErrors )
  }
  
  "OptionsValidator" should "fail validation if wrong option" in {
    // given
    val option2 = buildOption ( "00000004", "002" )
    val options = List[OptionData] ( option1, option2 )
    
    // when
    val res = optionsValidator.validateOptions(unitSurrogateNumbers, options)
    
    // then
    assert ( res.hasErrors )
    assert ( res.getErrors.size == 1 )
    assert ( res.getErrors ( 0 ).contains ( "Wrong unit surrogate" ) )
  }
  
  "OptionsValidator" should "fail validation if duplicated option" in {
    // given
    val option2 = buildOption ( "00000001", "002" )
    val option3 = buildOption ( "00000002", "001" )
    val option4 = buildOption ( "00000002", "002" )
    val option5 = buildOption ( "00000002", "001" )
    val options = List[OptionData] ( option1, option2, option3, option4, option5 )
    
    // when
    val res = optionsValidator.validateOptions(unitSurrogateNumbers, options)
    
    // then
    assert ( res.hasErrors )
    assert ( res.getErrors.size == 1 )
    assert ( res.getErrors ( 0 ).contains ( "Duplicated unit surrogate" ) )
  }
}
