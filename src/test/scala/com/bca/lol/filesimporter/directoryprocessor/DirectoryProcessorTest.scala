package com.bca.lol.filesimporter.directoryprocessor

import org.scalatest._
import org.scalatest.mock._
import com.bca.lol.filesimporter.validator.FilesValidator
import org.scalatest.junit.AssertionsForJUnit
import org.mockito.Mockito._
import org.mockito.Matchers._
import java.io.File
import com.bca.lol.filesimporter.parser.FilesParser
import com.bca.lol.filesimporter.filedata._
import com.bca.lol.filesimporter.validator.FilesContentValidator
import scala.util.{ Success, Failure }
import com.bca.lol.filesimporter.filedata.ImportedData
import com.bca.lol.filesimporter.dataconvertor.SaleConvertor
import com.bca.lol.filesimporter.data.Sale
import com.bca.lol.filesimporter.persistence.PersistenceManager
import com.bca.lol.filesimporter.validator.FilesValidator
import com.bca.lol.filesimporter.validator.FilesContentValidator
import com.bca.lol.filesimporter.persistence.PersistenceManager
import java.nio.file.Path
import java.nio.file.Files

class DirectoryProcessorTest extends FlatSpec with AssertionsForJUnit with MockitoSugar with BeforeAndAfter {
  val DIR = new File("123").toPath().toAbsolutePath()
  val LANG = 1
  val SALE_DIRECTORY = SaleDirectory(LANG, DIR)

  var directoryProcessor: DirectoryProcessor = _
  var mockFilesExtractor: FilesExtractor = _
  var mockFilesValidator: FilesValidator = _
  var mockFilesParser: FilesParser = _
  var mockFilesContentValidator: FilesContentValidator = _
  var mockSaleConvertor: SaleConvertor = _
  var mockPersistenceManager: PersistenceManager = _
  var mockDirectoryMover: DirectoryMover = _

  before {
    directoryProcessor = new DirectoryProcessor
    
    mockFilesExtractor = mock[FilesExtractor]
    directoryProcessor.filesExtractor = mockFilesExtractor

    mockFilesValidator = mock[FilesValidator]
    directoryProcessor.filesValidator = mockFilesValidator

    mockFilesParser = mock[FilesParser]
    directoryProcessor.filesParser = mockFilesParser

    mockFilesContentValidator = mock[FilesContentValidator]
    directoryProcessor.filesContentValidator = mockFilesContentValidator

    mockSaleConvertor = mock[SaleConvertor]
    directoryProcessor.saleConvertor = mockSaleConvertor
    
    mockPersistenceManager = mock[PersistenceManager]
    directoryProcessor.persistenceManager = mockPersistenceManager
    
    mockDirectoryMover = mock[DirectoryMover]
    directoryProcessor.directoryMover = mockDirectoryMover
  }

  "DirectoryProcessor" should "process successfully validation results" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    when(mockDirectoryMover.moveDirectory(SALE_DIRECTORY, DirectoryProcessor.PROCESSED_DIRECTORY, new ImportResult)).thenReturn(Success())

    // when
    val res = directoryProcessor.process(SALE_DIRECTORY)

    // then
    assert(res.hasNoErrors)
  }

  "DirectoryProcessor" should "fail if FilesExtractor fails" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale
    val msg = "FilesExtractor error"
    val errorRes = buildErrorResult(msg);

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Failure(buildException(msg)))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    when(mockDirectoryMover.moveDirectory(SALE_DIRECTORY, DirectoryProcessor.FAILED_DIRECTORY, errorRes)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SALE_DIRECTORY)

    // then
    assert(res.hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("FilesExtractor"))
  }

  "DirectoryProcessor" should "fail if FilesValidator fails" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale
    val msg = "FilesValidator error"
    val errorRes = buildErrorResult(msg)

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(errorRes)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    when(mockDirectoryMover.moveDirectory(SALE_DIRECTORY, DirectoryProcessor.FAILED_DIRECTORY, errorRes)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SALE_DIRECTORY)

    // then
    assert(res.hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("FilesValidator"))
  }

  "DirectoryProcessor" should "fail if FilesParser fails" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale
    val msg = "FilesParser error"
    val errorRes = buildErrorResult(msg);

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Failure(buildException(msg)))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    when(mockDirectoryMover.moveDirectory(SALE_DIRECTORY, DirectoryProcessor.FAILED_DIRECTORY, errorRes)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SALE_DIRECTORY)

    // then
    assert(res.hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("FilesParser"))
  }

  "DirectoryProcessor" should "fail if FilesContentValidator fails" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale
    val msg = "FilesContentValidator error"
    val errorRes = buildErrorResult(msg);

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(errorRes)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    when(mockDirectoryMover.moveDirectory(SALE_DIRECTORY, DirectoryProcessor.FAILED_DIRECTORY, errorRes)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SALE_DIRECTORY)

    // then
    assert(res.hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("FilesContentValidator"))
  }

  "DirectoryProcessor" should "fail if SaleConvertor fails" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale
    val msg = "SaleConvertor error"
    val errorRes = buildErrorResult(msg);

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Failure(buildException("SaleConvertor error")))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    when(mockDirectoryMover.moveDirectory(SALE_DIRECTORY, DirectoryProcessor.FAILED_DIRECTORY, errorRes)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SALE_DIRECTORY)

    // then
    assert(res.hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("SaleConvertor"))
  }

  "DirectoryProcessor" should "fail if PersistenceManager fails" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale
    val msg = "PersistenceManager error"
    val errorRes = buildErrorResult(msg);

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Failure(buildException("PersistenceManager error")))
    when(mockDirectoryMover.moveDirectory(SALE_DIRECTORY, DirectoryProcessor.FAILED_DIRECTORY, errorRes)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SALE_DIRECTORY)

    // then
    assert(res.hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0).contains("PersistenceManager"))
  }

  private def buildMockFiles = {
    val mockControlFile = buildMockFile(DirectoryProcessor.CONTROL)
    val mockSalelFile = buildMockFile(DirectoryProcessor.SALE)
    val mockUnitFile = buildMockFile(DirectoryProcessor.UNIT)
    val mockLotFile = buildMockFile(DirectoryProcessor.LOT)
    val mockOptionFile = buildMockFile(DirectoryProcessor.OPTION)
    val mockConditionFile = buildMockFile(DirectoryProcessor.CONDS)
    val mockCommentFile = buildMockFile(DirectoryProcessor.UCOMMENT)

    List[File](mockControlFile, mockSalelFile, mockUnitFile, mockLotFile, mockOptionFile, mockConditionFile, mockCommentFile)
  }

  private def buildMockFile(fileName: String) = {
    val mockFile = mock[File]
    when(mockFile.getName).thenReturn(fileName)
    mockFile
  }

  private def buildImportedData = {
    new ImportedData(new ControlData, 
        List[SaleData](), 
        List[UnitData](), 
        List[LotData](), 
        List[OptionData](), 
        List[ConditionData](), 
        List[CommentData]())
  }
  
  private def buildSale = {
    new Sale
  }
  
  private def buildException(errorMsg: String) = {
    new Exception(errorMsg)
  }
  
  private def buildErrorResult(errorMsg: String) = {
    val importResult = new ImportResult
    importResult.addError(errorMsg)
    importResult
  }
}
