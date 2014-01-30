package com.bca.lol.filesimporter.directoryprocessor

import com.bca.lol.filesimporter.validator.FilesValidator
import java.io.File
import com.bca.lol.filesimporter.validator.FilesContentValidator
import com.bca.lol.filesimporter.parser.FilesParser

object DirectoryProcessor {
  val CONTROL = "CONTROL"
  val SALE = "SALE"
  val UNIT = "UNIT"
  val LOT = "LOT"
  val OPTION = "OPTION"
  val CONDITION = "CONDITION"
  val COMMENT = "COMMENT"
  val CONDS = "CONDS"
  val UCOMMENT = "UCOMMENT"
    
  val SALE_FILES: List[String] = List(CONTROL, SALE, UNIT, LOT, OPTION, CONDITION, COMMENT);
}

class DirectoryProcessor {

  var filesExtractor = new FilesExtractor
  var filesValidator = new FilesValidator
  var filesParser = new FilesParser
  var filesContentValidator = new FilesContentValidator

  def processDirectory ( languageId: Int, directoryName: String ) : ImportResult = {
    println("Directory: " + directoryName)
    
    val files = filesExtractor.extractFiles ( directoryName )
    
    var res = filesValidator.validateFiles ( files.map ( _.getName ) )    
    if ( res.hasNoErrors ) return res
    
    val importedData = filesParser.parseFiles ( files )

    res = filesContentValidator.validateFilesContent ( importedData )    
    if ( res.hasNoErrors ) return res
    
    res
  }
}
