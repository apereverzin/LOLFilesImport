package com.bca.lol.filesimporter.filedata

sealed class OptionData extends FileData {
  var unitSurrogate: String = _
  var displaySequence: String = _
  var optionDescription: String = _
}