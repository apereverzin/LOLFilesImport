package com.bca.lol.filesimporter.data

case class Option(displaySequence: Int) extends Sequenced(displaySequence: Int) with LotElement {
  var optionDescription: String = _
}
