package com.bca.lol.filesimporter.validator

import org.scalatest._
import com.bca.lol.filesimporter.filedata.CommentData
import com.bca.lol.filesimporter.BuildingMethods

class CommentsValidatorTest extends FlatSpec with BeforeAndAfter with BuildingMethods {
  
  var commentsValidator : CommentsValidator = _
  
  val unitSurrogateNumbers = Set[String] ( "00000001", "00000002", "00000003" )

  val comment1 = buildComment ( "00000001", "0001" )
  val comment2 = buildComment ( "00000002", "0001" )
  
  before {
    commentsValidator = new CommentsValidator
  }

  "CommentsValidator" should "validate correct data" in {
    // given
    val comments = List[CommentData] ( comment1, comment2 )
    
    // when
    val res = commentsValidator.validateComments(unitSurrogateNumbers, comments)
    
    // then
    assert ( !res.hasErrors )
  }

  "CommentsValidator" should "fail validation for wrong unit surrogate" in {
    // given
    val comment3 = buildComment ( "00000004", "0001" )
    val comments = List[CommentData] ( comment1, comment2, comment3 )

    // when
    val res = commentsValidator.validateComments(unitSurrogateNumbers, comments)
    
    // then
    assert ( res.hasErrors )
    assert ( res.getErrors.size == 1 )
    assert ( res.getErrors ( 0 ).contains( "Wrong unit surrogate" ) )
  }

  "CommentsValidator" should "fail validation for duplicate sequence number" in {    
    // given
    val comment3 = buildComment ( "00000001", "0002" )
    val comment4 = buildComment ( "00000001", "0001" )
    val comments = List[CommentData] ( comment1, comment2, comment3, comment4 )
    
    // when
    val res = commentsValidator.validateComments(unitSurrogateNumbers, comments)
    
    // then
    assert ( res.hasErrors )
    assert ( res.getErrors.size == 1 )
    assert ( res.getErrors ( 0 ).contains( "Duplicate unit surrogate" ) )
  }
}
