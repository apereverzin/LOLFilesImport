package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.CommentData

class CommentDataParser extends FileLineParser {

  override def parseLine ( line: String ) = {
    val unitSurrogate = takeFirstField ( line, 7 )
    val commentType = takeNextField ( line, 1 )
    val sequenceNumber = takeNextField ( line, 3 )
    val comment = takeNextField ( line, 65 )
    
    CommentData(unitSurrogate, commentType, sequenceNumber, comment)
  }
}
