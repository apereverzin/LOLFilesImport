package com.bca.lol.filesimporter.filedata

sealed class LotData extends FileData {
  var surrogateNumber: String = _
  var saleNumber: String = _
  var lotNumber: String = _
  var description: String = _
  var startingPrice: Int = _
  var reservePrice: Int = _
  var catalogId: Int = _
  var contextId: Int = _
}
