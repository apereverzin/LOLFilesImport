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

  private val options : ListBuffer[Option] = ListBuffer[Option]()
  private val conditions : ListBuffer[Condition] = ListBuffer[Condition]()
  private val comments : ListBuffer[Comment] = ListBuffer[Comment]()
  
  def addOption(option: Option) = options += option
  def getOptions = options.toList
  def addCondition(condition: Condition) = conditions += condition
  def getConditions = conditions.toList
  def addComment(comment: Comment) = comments += comment
  def getComments = comments.toList
}
