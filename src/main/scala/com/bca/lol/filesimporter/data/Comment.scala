package com.bca.lol.filesimporter.data

case class Comment(displaySequence: Int)  extends Sequenced(displaySequence: Int) {
  var commentType: String = _
  var comment: String = _
}