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
import com.bca.lol.filesimporter.directoryprocessor.DirectoryProcessor
import scala.collection.mutable.Map
import scala.io.Source
import com.bca.lol.filesimporter.filedata.ControlData
import com.bca.lol.filesimporter.filedata.FileData

class FilesParser {
  var controlDataParser = new ControlDataParser
  var saleDataParser = new SaleDataParser
  var lotDataParser = new LotDataParser
  var unitDataParser = new UnitDataParser
  var optionDataParser = new OptionDataParser
  var conditionDataParser = new ConditionDataParser
  var commentDataParser = new CommentDataParser

  def parseFiles(files: List[File]): (ControlData, List[SaleData], List[UnitData], List[LotData], List[OptionData], List[ConditionData], 
      List[CommentData]) = {

    val fileNames: Map[String, File] = new HashMap[String, File]();
    files.foreach(f => fileNames.put(f.getName, f))
        
    val controlData = parseControlFile(fileNames)
    val saleData = parseFile(fileNames, DirectoryProcessor.SALE, saleDataParser)
    val unitData = parseFile(fileNames, DirectoryProcessor.UNIT, unitDataParser)
    val lotData = parseFile(fileNames, DirectoryProcessor.LOT, lotDataParser)
    val optionData = parseFile(fileNames, DirectoryProcessor.OPTION, optionDataParser)
    val conditionData = parseFile(fileNames, DirectoryProcessor.CONDS, conditionDataParser)
    val commentData = parseFile(fileNames, DirectoryProcessor.UCOMMENT, commentDataParser)
    
    (controlData, saleData.asInstanceOf[List[SaleData]], unitData.asInstanceOf[List[UnitData]], lotData.asInstanceOf[List[LotData]], 
        optionData.asInstanceOf[List[OptionData]], conditionData.asInstanceOf[List[ConditionData]], commentData.asInstanceOf[List[CommentData]])
  }
  
  private def parseControlFile(fileNames: Map[String, File]): ControlData = {
    val file = fileNames.get(DirectoryProcessor.CONTROL).get
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
