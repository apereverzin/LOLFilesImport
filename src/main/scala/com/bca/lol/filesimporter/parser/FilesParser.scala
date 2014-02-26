package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.ControlData
import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.filedata.UnitData
import com.bca.lol.filesimporter.filedata.OptionData
import com.bca.lol.filesimporter.filedata.ConditionData
import com.bca.lol.filesimporter.filedata.CommentData
import java.io.File
import scala.collection.mutable.HashMap
import com.bca.lol.filesimporter.directoryprocessor.DirectoryProcessor._
import scala.collection.mutable.Map
import scala.io.Source
import com.bca.lol.filesimporter.filedata.ControlData
import com.bca.lol.filesimporter.filedata.FileData
import com.bca.lol.filesimporter.filedata.ImportedData
import scala.util.{ Try, Success, Failure }

class FilesParser {
  var controlDataParser = new ControlDataParser
  var saleDataParser = new SaleDataParser
  var lotDataParser = new LotDataParser
  var unitDataParser = new UnitDataParser
  var optionDataParser = new OptionDataParser
  var conditionDataParser = new ConditionDataParser
  var commentDataParser = new CommentDataParser

  def parseFiles(files: List[File]): Try[ImportedData] = {

    try {
      val fileNames: Map[String, File] = new HashMap[String, File]();
      files.foreach(f => fileNames.put(f.getName, f))

      val controlData = parseControlFile(fileNames)
      val saleData = parseFile(fileNames, SALE, saleDataParser).asInstanceOf[List[SaleData]]
      val unitData = parseFile(fileNames, UNIT, unitDataParser).asInstanceOf[List[UnitData]]
      val lotData = parseFile(fileNames, LOT, lotDataParser).asInstanceOf[List[LotData]]
      val optionData = parseFile(fileNames, OPTION, optionDataParser).asInstanceOf[List[OptionData]]
      val conditionData = parseFile(fileNames, CONDS, conditionDataParser).asInstanceOf[List[ConditionData]]
      val commentData = parseFile(fileNames, UCOMMENT, commentDataParser).asInstanceOf[List[CommentData]]

      Success(new ImportedData(controlData, saleData, unitData, lotData, optionData, conditionData, commentData))
    } catch {
      case e: Throwable => Failure(e)
    }
  }

  private def parseControlFile(fileNames: Map[String, File]): ControlData = {
    val file = fileNames.get(CONTROL).get
    val bufferedSource = Source.fromFile(file)
    val controlData = controlDataParser.parseControlFileLines(bufferedSource.getLines)
    bufferedSource.close
    controlData
  }

  private def parseFile(fileNames: Map[String, File], fileName: String, parser: FileParser) = {
    val file = fileNames.get(fileName).get
    parser.parseLines(file)
  }
}
