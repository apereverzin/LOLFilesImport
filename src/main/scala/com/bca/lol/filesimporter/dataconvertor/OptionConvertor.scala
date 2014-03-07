package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.OptionData
import com.bca.lol.filesimporter.data.LotOption
import scala.util.{Try, Success, Failure}

class OptionConvertor {
  def convertOption (optionData: OptionData): Try[LotOption] = {
    val displaySequence = Try[Int](Integer.parseInt(optionData.displaySequence))
    println(s"convertOption ${optionData.displaySequence}")
    
    displaySequence match {
      case Success(s) => {
            val option = new LotOption
            option.displaySequence = displaySequence.get
            option.description = optionData.description
            Success(option)
      }
      case Failure(f) => Failure[LotOption](f)
    }
  }
}
