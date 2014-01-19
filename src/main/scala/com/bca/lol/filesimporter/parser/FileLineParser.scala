package com.bca.lol.filesimporter.parser

import java.io.File
import com.bca.lol.filesimporter.filedata.FileData

abstract class FileLineParser {
  var ind = 0
  
  def readLines ( file: File ) = {
    List[String]()
  }
  
  def parseLine ( line: String ) : FileData
  
  def takeFirstField ( line: String, width: Int ) = {
    ind = width
    line.substring ( ind - width, ind ).trim
  }
  
  def takeNextField ( line: String, width: Int ) = {
    ind += width
    line.substring ( ind - width, ind ).trim
  }
}
