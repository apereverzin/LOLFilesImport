package com.bca.lol.filesimporter.data

case class Option(displaySequence: Int) extends Sequenced(displaySequence: Int) {
  var optionDescription: String = _
}
