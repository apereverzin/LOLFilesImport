package com.bca.lol.filesimporter.parser

import java.io.File
import com.bca.lol.filesimporter.filedata.FileData
import scala.io.Source

abstract class FileParser {
  var ind = 0

  def parseLines [T >: FileData] (file: File): List[T] = {
    val bufferedSource = Source.fromFile(file)
    var lines = List[FileData]()
    for (line <- bufferedSource.getLines) {
      if (line.trim.length > 0) lines = parseLine(line) :: lines
    }
    bufferedSource.close
    lines
  }

  def parseLine(line: String): FileData

  def takeFirstField(line: String, width: Int) = {
    ind = width
    line.substring(0, ind).trim
  }

  def takeNextField(line: String, width: Int) = {
    ind += width
    try {
      line.substring(ind - width, ind).trim
    } catch {
      case _: Throwable => ""
    }
  }

  def parseFile(file: File) = {
    file.list()
  }
}
