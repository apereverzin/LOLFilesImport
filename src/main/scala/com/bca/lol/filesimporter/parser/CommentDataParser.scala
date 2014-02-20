package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.CommentData

class CommentDataParser extends FileParser {

  override def parseLine(line: String) = {
    val commentData = new CommentData

    takeFirstField(line, 7)
    commentData.commentType = takeNextField(line, 1)
    commentData.sequenceNumber = takeNextField(line, 3)
    commentData.comment = takeNextField(line, 65)
    commentData.unitSurrogate = takeNextField(line, 9)

    commentData
  }
}
