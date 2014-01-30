package com.bca.lol.filesimporter.dataconvertor

import com.bca.lol.filesimporter.filedata.CommentData
import com.bca.lol.filesimporter.data.Comment
import scala.util.Try
import scala.util.Success
import scala.util.Failure

class CommentConvertor {
  def convertComment (commentData: CommentData): Try[Comment] = {
    val displaySequence = Try[Int](Integer.parseInt(commentData.sequenceNumber))

    displaySequence match {
      case Success(s) => {
            val comment = new Comment(displaySequence.get)
            comment.comment = commentData.comment
            Success(comment)
      }
      case Failure(f) => Failure[Comment](f)
    }
  }
}
