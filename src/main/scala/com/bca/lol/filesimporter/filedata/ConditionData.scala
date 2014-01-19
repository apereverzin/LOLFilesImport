package com.bca.lol.filesimporter.filedata

sealed class ConditionData extends FileData {
  var unitSurrogate: String = _
  var inspCompCondDesc: String = _
  var status: String = _
}
