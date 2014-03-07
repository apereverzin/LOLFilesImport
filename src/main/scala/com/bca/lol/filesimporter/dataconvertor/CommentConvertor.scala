package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.CommentData
import com.bca.lol.filesimporter.data.LotComment
import scala.util.{Try, Success, Failure}

class CommentConvertor {
  def convertComment (commentData: CommentData): Try[LotComment] = {
    println(s"convertComment ${commentData.sequenceNumber}")
    val displaySequence = Try[Int](Integer.parseInt(commentData.sequenceNumber))

    displaySequence match {
      case Success(s) => {
            val comment = new LotComment
            comment.displaySequence = displaySequence.get
            comment.comment = commentData.comment
            comment.commentType = commentData.commentType
            Success(comment)
      }
      case Failure(f) => Failure[LotComment](f)
    }
  }
}
