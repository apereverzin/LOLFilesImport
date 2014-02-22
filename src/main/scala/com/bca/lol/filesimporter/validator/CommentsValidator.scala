package com.bca.lol.filesimporter.validator

import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import scala.collection.mutable.Set
import scala.collection.immutable.Set
import com.bca.lol.filesimporter.filedata.CommentData

class CommentsValidator {
  def validateComments(unitSurrogateNumbers: scala.collection.immutable.Set[String], comments: List[CommentData]) = {
    val res = new ImportResult

    val commentUnitSurrogateAndSequenceNumbers = scala.collection.mutable.Set[(String, String)]()

    for (comment <- comments) {
      val unitSurrogate = comment.surrogateNumber
      if (!unitSurrogateNumbers.contains(unitSurrogate)) res.addError(buildCommentUnitSurrogateError(unitSurrogate))
      if (commentUnitSurrogateAndSequenceNumbers.contains((unitSurrogate, comment.sequenceNumber)))
        res.addError(buildCommentUnitSurrogateAndSequenceNumberDuplicationError(unitSurrogate, comment.sequenceNumber))
      else
        commentUnitSurrogateAndSequenceNumbers += ((unitSurrogate, comment.sequenceNumber))
    }

    res
  }

  def buildCommentUnitSurrogateError(unitSurrogate: String) =
    s"Wrong unit surrogate $unitSurrogate for comment"

  def buildCommentUnitSurrogateAndSequenceNumberDuplicationError(unitSurrogate: String, sequenceNumber: String) =
    s"Duplicate unit surrogate $unitSurrogate and sequence number $sequenceNumber for comment"
}
