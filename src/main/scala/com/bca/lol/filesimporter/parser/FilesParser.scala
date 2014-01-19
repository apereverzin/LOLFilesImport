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

class FilesParser {
  var controlDataParser = new ControlDataParser
  var saleDataParser = new SaleDataParser
  var lotDataParser = new LotDataParser
  var unitDataParser = new UnitDataParser
  var optionDataParser = new OptionDataParser
  var conditionDataParser = new ConditionDataParser
  var commentDataParser = new CommentDataParser
  
  def parseFiles ( files: List[File] ) = {
    
    val fileNames: Map[String, File] = new HashMap[String, File]();
    files.foreach ( s => fileNames.put ( s.getName, s ) )
    val controlData = controlDataParser.parseLines ( fileNames.get(DirectoryProcessor.CONTROL).get.list )
    
    ( controlData, List[SaleData](), List[UnitData](), List[LotData](), 
      List[OptionData](), List[ConditionData](), List[CommentData]() )
  }
}