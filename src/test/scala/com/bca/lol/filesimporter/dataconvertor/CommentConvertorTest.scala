package com.bca.lol.filesimporter.dataconvertor

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter
import com.bca.lol.filesimporter.BuildingMethods
import com.bca.lol.filesimporter.filedata._

class CommentConvertorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  val UnitSurrogate1 = "00000001"
  val DisplaySequence1 = "001"
  val DisplaySequence2 = "a02"
  val Comment1 = "Comment 1"
  val Comment2 = "Comment 2"

  var commentConvertor: CommentConvertor = _

  before {
    commentConvertor = new CommentConvertor
  }

  "ConditionConvertor" should "convert valid condition" in {
    // given
    val comment = buildCommentData(sequenceNumber = DisplaySequence1, comment = Comment1)

    // when
    val convertedComment = commentConvertor.convertComment(comment)

    // then
    assert(convertedComment.isSuccess)
    assert(convertedComment.get.displaySequence == 1)
    assert(convertedComment.get.comment == Comment1)
  }

  "ConditionConvertor" should "fail to convert comment with incorrect display sequence" in {
    // given
    val comment = buildCommentData(sequenceNumber = DisplaySequence2, comment = Comment1)

    // when
    val convertedComment = commentConvertor.convertComment(comment)

    // then
    assert(convertedComment.isFailure)
    assert(convertedComment.failed.get.getMessage().contains("For input string"))
  }
}
