package com.bca.lol.filesimporter.dataconvertor

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods
import com.bca.lol.filesimporter.filedata._

class OptionConvertorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val UnitSurrogate1 = "00000001"
  val DisplaySequence1 = "001"
  val DisplaySequence2 = "a02"
  val OptionDescription1 = "Descr 1"
  val OptionDescription2 = "Descr 2"

  var optionConvertor: OptionConvertor = _

  before {
    optionConvertor = new OptionConvertor
  }

  "OptionConvertor" should "convert valid option" in {
    // given
    val option = buildOptionData(UnitSurrogate1, DisplaySequence1, OptionDescription1)

    // when
    val convertedOption = optionConvertor.convertOption(option)

    // then
    assert(convertedOption.isSuccess)
    assert(convertedOption.get.description == OptionDescription1)
    assert(convertedOption.get.displaySequence == 1)
  }

  "OptionConvertor" should "fail to convert option with incorrect display sequence" in {
    // given
    val option = buildOptionData(UnitSurrogate1, DisplaySequence2, OptionDescription1)

    // when
    val convertedOption = optionConvertor.convertOption(option)

    // then
    assert(convertedOption.isFailure)
    assert(convertedOption.failed.get.getMessage().contains("For input string"))
  }
}
