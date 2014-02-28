package com.bca.lol.filesimporter.parser

import java.io.File
import com.bca.lol.filesimporter.filedata.FileData
import scala.io.Source
import scala.collection.mutable.ListBuffer

abstract class FileParser {
  var ind = 0

  def parseLines [T >: FileData] (file: File): List[T] = {
    println(s"FileParser parseLines ${file.getName}")
    val bufferedSource = Source.fromFile(file)
    val lines = ListBuffer[FileData]()
    for (line <- bufferedSource.getLines) {
      //println(s"parsing line ${line}")
      if (line.trim.length > 0) lines += parseLine(line)
    }
    bufferedSource.close
    lines.toList
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
      case e: Throwable => ""
    }
  }

  def parseFile(file: File) = {
    file.list
  }
}
