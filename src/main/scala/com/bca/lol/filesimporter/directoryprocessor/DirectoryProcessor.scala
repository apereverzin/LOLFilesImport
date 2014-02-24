package com.bca.lol.filesimporter.directoryprocessor

import akka.actor.{ Actor, ActorLogging, Props }
import com.bca.lol.filesimporter.validator.{ FilesValidator, FilesContentValidator }
import com.bca.lol.filesimporter.parser.FilesParser
import java.io.File
import com.bca.lol.filesimporter.dataconvertor.SaleConvertor
import scala.util.{ Success, Failure }
import com.bca.lol.filesimporter.persistence.PersistenceManager

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
  var persistenceManager = new PersistenceManager

  def receive = {
    case dir: Directory => sender ! (dir, process(dir))
  }

  def process(dir: Directory): ImportResult = {
    val directoryName = dir.directoryName
    println(s"Directory: $directoryName")

    val files = filesExtractor.extractFiles(directoryName)
    files match {
      case Success(s) =>
      case Failure(e: Throwable) => return processError(e)
    }

    var res = filesValidator.validateFiles(files.get.filterNot(_.getName.startsWith(".")).map(_.getName))
    if (res.hasErrors) return res

    val importedData = filesParser.parseFiles(files.get)
    importedData match {
      case Success(s) =>
      case Failure(e: Throwable) => return processError(e)
    }

    res = filesContentValidator.validateFilesContent(importedData.get)
    if (res.hasErrors) return res

    val sale = saleConvertor.convertSale(res, importedData.get)
    sale match {
      case Success(s) =>
      case Failure(e: Throwable) => return processError(e)
    }

    persistenceManager.persistSale(sale.get) match {
      case Success(_) => res
      case Failure(e: Throwable) => return processError(e)
    }
  }

  private def processError(e: Throwable): ImportResult = {
    val res = new ImportResult();
    res.addError(e.getMessage());
    res
  }
}
