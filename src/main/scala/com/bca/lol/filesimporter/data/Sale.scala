package com.bca.lol.filesimporter.data

import scala.collection.mutable.ListBuffer
import java.sql.Date

case class Sale() extends Identifiable {
  var contextId: Int = _
  var name: String = _
  var description: String = _
  var auctionEnd: Date = _
  var auctionStart: Date = _
  var status: Int = _
  var accessControl: Int = _
  var locked: Int = _
  var created: Date = _
  var catalogType: Int = _
  var extended: String = _
  var statusText: String = _
  var reference: String = _
  var lastModified: Date = _
  var ims: Int = _
  var defaultLanguage: String = _
  var documentLanguage: String = _
  
  var lots : List[Lot] = _
}
