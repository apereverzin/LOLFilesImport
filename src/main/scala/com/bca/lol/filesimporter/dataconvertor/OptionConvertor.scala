package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.OptionData
import com.bca.lol.filesimporter.data.Option
import scala.util.Try
import scala.util.Success
import scala.util.Failure
import scala.util.Failure

class OptionConvertor {
  def convertOption (optionData: OptionData): Try[Option] = {
    val displaySequence = Try[Int](Integer.parseInt(optionData.displaySequence))
    
    displaySequence match {
      case Success(s) => {
            val option = new Option(displaySequence.get)
            option.optionDescription = optionData.optionDescription
            Success(option)
      }
      case Failure(f) => Failure[Option](f)
    }
  }
}
