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
      val unitSurrogate = comment.unitSurrogate
      if (!unitSurrogateNumbers.contains(unitSurrogate)) res.addError(buildCommentUnitSurrogateError(unitSurrogate))
      if (commentUnitSurrogateAndSequenceNumbers.contains((unitSurrogate, comment.sequenceNumber)))
        res.addError(buildCommentUnitSurrogateAndSequenceNumberDuplicationError(unitSurrogate, comment.sequenceNumber))
      else
        commentUnitSurrogateAndSequenceNumbers += ((unitSurrogate, comment.sequenceNumber))
    }

    res
  }

  def buildCommentUnitSurrogateError(unitSurrogate: String) =
    String.format("Wrong unit surrogate %s for comment", unitSurrogate)

  def buildCommentUnitSurrogateAndSequenceNumberDuplicationError(unitSurrogate: String, sequenceNumber: String) =
    String.format("Duplicate unit surrogate %s and sequence number %s for comment", unitSurrogate, sequenceNumber)
}
