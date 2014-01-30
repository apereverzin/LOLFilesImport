package com.bca.lol.filesimporter.directoryprocessor

import org.scalatest._
import org.scalatest.mock._
import com.bca.lol.filesimporter.validator.FilesValidator
import org.scalatest.junit.AssertionsForJUnit
import org.mockito.Mockito._
import java.io.File
import com.bca.lol.filesimporter.parser.FilesParser
import com.bca.lol.filesimporter.filedata.ControlData
import com.bca.lol.filesimporter.filedata.SaleData
import com.bca.lol.filesimporter.filedata.UnitData
import com.bca.lol.filesimporter.filedata.LotData
import com.bca.lol.filesimporter.filedata.CommentData
import com.bca.lol.filesimporter.filedata.ConditionData
import com.bca.lol.filesimporter.filedata.OptionData
import com.bca.lol.filesimporter.validator.FilesContentValidator

class DirectoryProcessorTest extends FlatSpec with AssertionsForJUnit with MockitoSugar with BeforeAndAfter {
  val DIR = "123"
  val LANG = 1

  "DirectoryProcessor" should "process success validation results" in {
    // given
    val mockFiles = buildMockFiles
    val mockImportedData = buildMockImportedData

    val directoryProcessor = new DirectoryProcessor

    val mockFilesExtractor = mock[FilesExtractor]
    directoryProcessor.filesExtractor = mockFilesExtractor
    when(mockFilesExtractor.extractFiles(DIR)).thenReturn(mockFiles)

    val mockFilesValidator = mock[FilesValidator]
    directoryProcessor.filesValidator = mockFilesValidator
    when(mockFilesValidator.validateFiles(mockFiles.map(_.getName))).thenReturn(new ImportResult)

    val mockFilesParser = mock[FilesParser]
    directoryProcessor.filesParser = mockFilesParser
    when(mockFilesParser.parseFiles(mockFiles)).thenReturn(mockImportedData)

    val mockFilesContentValidator = mock[FilesContentValidator]
    directoryProcessor.filesContentValidator = mockFilesContentValidator
    when(mockFilesContentValidator.validateFilesContent(mockImportedData)).thenReturn(new ImportResult)

    // when
    val res = directoryProcessor.processDirectory(LANG, DIR)

    // then
    assert(res hasNoErrors)
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

  private def buildMockImportedData = {
    (new ControlData, List[SaleData](), List[UnitData](), List[LotData](), List[OptionData](), List[ConditionData](), List[CommentData]())
  }
}
