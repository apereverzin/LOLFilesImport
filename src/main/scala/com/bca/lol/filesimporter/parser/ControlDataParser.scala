package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.ControlData
import java.io.File
import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import com.bca.lol.filesimporter.directoryprocessor.DirectoryProcessor
import scala.collection.Iterator

class ControlDataParser {

  def parseControlFileLines(lines: Iterator[String]) = {
    val controls: Map[String, Int] = new HashMap[String, Int]()
    val controlData = new ControlData

    for (line <- lines) {
      val v = parseLine(line)
      v._1 match {
        case DirectoryProcessor.SALE => controlData.salesNumber = v._2
        case DirectoryProcessor.UNIT => controlData.unitsNumber = v._2
        case DirectoryProcessor.LOT => controlData.lotsNumber = v._2
        case DirectoryProcessor.OPTION => controlData.optionsNumber = v._2
        case DirectoryProcessor.CONDS => controlData.conditionsNumber = v._2
        case DirectoryProcessor.UCOMMENT => controlData.commentsNumber = v._2
      }
    }

    controlData
  }

  private def parseLine(line: String) = {
    (line.substring(0, 8).trim(), Integer.parseInt(line.substring(8)))
  }
}
