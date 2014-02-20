package com.bca.lol.filesimporter.parser

import org.scalatest.FlatSpec
import org.scalatest.BeforeAndAfter

class CommentDataParserTest extends FlatSpec with BeforeAndAfter {
  var commentDataParser: CommentDataParser = _

  before {
    commentDataParser = new CommentDataParser
  }
  
  "CommentDataParser" should "parse correct data" in {
    // given
    
    // when
    val commentData = commentDataParser.parseLine ( "0000002X001Grade 4                                                          000000002" )
    
    // then
    assert ( commentData.unitSurrogate == "000000002" )
    assert ( commentData.commentType == "X" )
    assert ( commentData.sequenceNumber == "001" )
    assert ( commentData.comment == "Grade 4" )
  }
}
