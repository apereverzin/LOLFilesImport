package com.bca.lol.filesimporter.data

import scala.collection.mutable.ListBuffer

case class Lot() extends Identifiable with Sequenced {
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
  var locked: Int = _
  var lotType: Int = _
  var displayId: String = _
  var saleCode: String = _
  var saleId: Int = _

  var options: List[LotOption] = _
  var conditions: List[LotCondition] = _
  var comments: List[LotComment] = _
}
