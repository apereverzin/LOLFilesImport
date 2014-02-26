package com.bca.lol.filesimporter.directoryprocessor

import org.scalatest._
import org.scalatest.mock._
import com.bca.lol.filesimporter.validator.FilesValidator
import org.scalatest.junit.AssertionsForJUnit
import org.mockito.Mockito._
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

  var directoryProcessor: DirectoryProcessor = _
  var mockFilesExtractor: FilesExtractor = _
  var mockFilesValidator: FilesValidator = _
  var mockFilesParser: FilesParser = _
  var mockFilesContentValidator: FilesContentValidator = _
  var mockSaleConvertor: SaleConvertor = _
  var mockPersistenceManager: PersistenceManager = _

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
    
    // when
    val res = directoryProcessor.process(SaleDirectory(LANG, DIR))

    // then
    assert(res.hasNoErrors)
  }

  "DirectoryProcessor" should "fail if FilesExtractor fails" in {
    // given
    val mockFiles = buildMockFiles
    val importedData = buildImportedData
    val sale = buildSale

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Failure(buildException("FilesExtractor error")))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SaleDirectory(LANG, DIR))

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

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(buildImportResult("FilesValidator error"))
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SaleDirectory(LANG, DIR))

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

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Failure(buildException("FilesParser error")))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SaleDirectory(LANG, DIR))

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

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(buildImportResult("FilesContentValidator error"))
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SaleDirectory(LANG, DIR))

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

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Failure(buildException("SaleConvertor error")))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Success())
    
    // when
    val res = directoryProcessor.process(SaleDirectory(LANG, DIR))

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

    when(mockFilesExtractor.extractFiles(DIR.toFile())).thenReturn(Success(mockFiles))
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(Success(importedData))
    when(mockFilesContentValidator.validateFilesContent(importedData)).thenReturn(new ImportResult)
    when(mockSaleConvertor.convertSale(importedData)).thenReturn(Success(sale))
    when(mockPersistenceManager.persistSale(sale)).thenReturn(Failure(buildException("PersistenceManager error")))
    
    // when
    val res = directoryProcessor.process(SaleDirectory(LANG, DIR))

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
  
  private def buildImportResult(errorMsg: String) = {
    val importResult = new ImportResult
    importResult.addError(errorMsg)
    importResult
  }
}
