package com.bca.lol.filesimporter.filedata

sealed class SaleData extends FileData {
  var saleNumber: String = _
  var saleCode: String = _
  var saleDescription: String = _
  var userGroupCode: String = _
  var saleDate: String = _
  var saleTime: String = _
  var saleVersion: String = _
}
