package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.filedata.OptionData
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set
import scala.collection.immutable.Set

class OptionsValidator {
  def validateOptions(unitSurrogateNumbers: scala.collection.immutable.Set[String], options: List[OptionData]) = {
    val res = new ImportResult

    val optionSurrogateNumbersAndDisplaySequences = scala.collection.mutable.Set[(String, String)]()

    for (option <- options) {
      val unitSurrogate = option.unitSurrogate
      if (!unitSurrogateNumbers.contains(unitSurrogate)) res.addError(buildOptionUnitSurrogateError(unitSurrogate))
      if (optionSurrogateNumbersAndDisplaySequences.contains((unitSurrogate, option.displaySequence)))
        res.addError(buildOptionUnitSurrogateAndDisplaySequenceDuplicationError(unitSurrogate, option.displaySequence))
      else
        optionSurrogateNumbersAndDisplaySequences += ((unitSurrogate, option.displaySequence))
    }

    res
  }

  def buildOptionUnitSurrogateError(unitSurrogate: String) =
    s"Wrong unit surrogate $unitSurrogate for option"

  def buildOptionUnitSurrogateAndDisplaySequenceDuplicationError(unitSurrogate: String, displaySequence: String) =
    s"Duplicated unit surrogate $unitSurrogate and display sequence $displaySequence for option"
}
