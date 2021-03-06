package com.bca.lol.filesimporter.directoryprocessor

import com.bca.lol.filesimporter.validator.{ FilesValidator, FilesContentValidator }
import com.bca.lol.filesimporter.parser.FilesParser
import java.io.File
import com.bca.lol.filesimporter.dataconvertor.SaleConvertor
import scala.util.{ Try, Success, Failure }
import com.bca.lol.filesimporter.persistence.PersistenceManager
import java.io.FilenameFilter
import com.bca.lol.filesimporter.filedata.ImportedData
import com.bca.lol.filesimporter.data.Sale
import java.nio.file.Files
import java.nio.file.Path

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

  val PROCESSED_DIRECTORY = "processed"
  val FAILED_DIRECTORY = "failed"
}

class DirectoryProcessor {
  var filesExtractor = new FilesExtractor
  var filesValidator = new FilesValidator
  var filesParser = new FilesParser
  var filesContentValidator = new FilesContentValidator
  var saleConvertor = new SaleConvertor
  var persistenceManager = new PersistenceManager
  var directoryMover = new DirectoryMover

  def process(saleDirectory: SaleDirectory): ImportResult = {
    println(s"process ${saleDirectory.directory.getFileName}")
    
    val directory = saleDirectory.directory.toFile()
    println(s"Directory: $directory")

    processFiles(saleDirectory, filesExtractor.extractFiles(saleDirectory.directory.toFile()))
  }

  private def processFiles(saleDirectory: SaleDirectory, files: Try[List[File]]): ImportResult = {
    println(s"processFiles ${saleDirectory.directory.getFileName}")
    files match {
      case Success(_) => validateFiles(saleDirectory, files.get)
      case Failure(e: Throwable) => processError(saleDirectory, e)
    }
  }

  private def validateFiles(saleDirectory: SaleDirectory, files: List[File]): ImportResult = {
    println(s"validateFiles ${saleDirectory.directory.getFileName}")
    var res = filesValidator.validateFiles(files.filterNot(_.getName.startsWith(".")).map(_.getName))
    if (res.hasErrors) return moveDirToDest(saleDirectory, DirectoryProcessor.FAILED_DIRECTORY, res)
    parseFiles(saleDirectory, files)
  }

  private def parseFiles(saleDirectory: SaleDirectory, files: List[File]): ImportResult = {
    println(s"parseFiles ${saleDirectory.directory.getFileName}")
    val importedData = filesParser.parseFiles(files)
    importedData match {
      case Success(_) => validateFilesContent(saleDirectory, importedData.get)
      case Failure(e: Throwable) => processError(saleDirectory, e)
    }
  }

  private def validateFilesContent(saleDirectory: SaleDirectory, importedData: ImportedData): ImportResult = {
    println(s"validateFilesContent ${saleDirectory.directory.getFileName}")
    val res = filesContentValidator.validateFilesContent(importedData)
    if (res.hasErrors) return moveDirToDest(saleDirectory, DirectoryProcessor.FAILED_DIRECTORY, res)
    convertSale(saleDirectory, importedData)
  }

  private def convertSale(saleDirectory: SaleDirectory, importedData: ImportedData): ImportResult = {
    println(s"convertSale ${saleDirectory.directory.getFileName}")
    val sale = saleConvertor.convertSale(importedData)
    sale match {
      case Success(_) => persistSale(saleDirectory, sale.get)
      case Failure(e: Throwable) => processError(saleDirectory, e)
    }
  }

  private def persistSale(saleDirectory: SaleDirectory, sale: Sale): ImportResult = {
    println(s"persistSale ${saleDirectory.directory.getFileName}")
    persistenceManager.persistSale(sale) match {
      case Success(_) => moveDirToDest(saleDirectory, DirectoryProcessor.PROCESSED_DIRECTORY, new ImportResult)
      case Failure(e: Throwable) => processError(saleDirectory, e)
    }
  }

  private def processError(saleDirectory: SaleDirectory, e: Throwable): ImportResult = {
    val res = new ImportResult()
    res.addError(e.getMessage())
    moveDirToDest(saleDirectory, DirectoryProcessor.FAILED_DIRECTORY, res)
  }

  private def moveDirToDest(saleDirectory: SaleDirectory, dest: String, res: ImportResult): ImportResult = {
    directoryMover.moveDirectory(saleDirectory, dest, res) match {
      case Success(_) => res
      case Failure(e: Throwable) => { res.addError(e.getMessage()); res }
    }
  }
}
