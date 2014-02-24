package com.bca.lol.filesimporter.data

import scala.collection.mutable.ListBuffer

case class Lot(displaySequence: Int) extends Sequenced(displaySequence: Int) {
  var contextId: Int = _
  var lotId: Int = _
  var vendor: Int = _
  var title: String = _
  var link: String = _
  var image: String = _
  var description: String = _
  var bid: Int = _
  var bidCount: Int = _
  var reserve: Int = _
  var minimumBid: Int = _
  var price: Int = _
  var currency: String = _
  var category: Int = _
  var template: Int = _
  var flags: Int = _
  var status: Int = _
  var quantity: Int = _
  var auctionEnd: Long = _
  var auctionStart: Long = _
  var locked: Int = _
  var created: Long = _
  var lotType: Int = _
  var displayId: String = _
  var saleCode: String = _
  var catalogId: Int = _

  var options: List[Option] = _
  var conditions: List[Condition] = _
  var comments: List[Comment] = _
}
