package com.bca.lol.filesimporter.data

class Sale {
  var context: Int = _
  var id: Int = _
  var name: String = _
  var description: String = _
  var auctionEnd: Long = _
  var auctionStart: Long = _
  var status: Int = _
  var accessControl: Int = _
  var locked: Int = _
  var created: Long = _
  var catalogType: Int = _
  var extended: String = _
  var statusText: String = _
  var reference: String = _
  var lastModified: Long = _
  var ims: Int = _
  var defaultLanguage: String = _
  var documentLanguage: String = _
  var lots : List[Lot] = _
}