package com.bca.lol.filesimporter.directoryprocessor

import akka.actor.{ Actor, ActorLogging, Props }
import com.bca.lol.filesimporter.validator.{ FilesValidator, FilesContentValidator }
import com.bca.lol.filesimporter.parser.FilesParser
import java.io.File
import com.bca.lol.filesimporter.dataconvertor.SaleConvertor

object DirectoryProcessor {
  val CONTROL = "CONTROL"
  val SALE = "SALE"
  val UNIT = "UNIT"
  val LOT = "LOT"
  val OPTION = "OPTION"
  val CONDS = "CONDS"
  val UCOMMENT = "UCOMMENT"
  val PRICES = "PRICES"

  val MANDATORY_SALE_FILES = List(CONTROL, SALE, UNIT, LOT, OPTION, CONDS, UCOMMENT);
  val OPTIONAL_SALE_FILES = List(PRICES);
}

class DirectoryProcessor extends Actor with ActorLogging {
  var filesExtractor = new FilesExtractor
  var filesValidator = new FilesValidator
  var filesParser = new FilesParser
  var filesContentValidator = new FilesContentValidator
  var saleConvertor = new SaleConvertor

  def receive = {
    case dir: Directory => sender ! (dir, process(dir))
  }

  def process(dir: Directory): ImportResult = {
    val directoryName = dir.directoryName
    println(s"Directory: $directoryName")

    val files = filesExtractor.extractFiles(directoryName)

    var res = filesValidator.validateFiles(files.filterNot(_.getName.startsWith(".")).map(_.getName))
    if (res.hasErrors) return res

    val importedData = filesParser.parseFiles(files)

    res = filesContentValidator.validateFilesContent(importedData)
    if (res.hasErrors) return res
    
    saleConvertor.convertSale(importedData._2(0), importedData._3, importedData._4, importedData._5, importedData._6, importedData._7)

    res
  }
}
