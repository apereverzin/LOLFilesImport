package com.bca.lol.filesimporter.validator

import scala.collection.immutable.List
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.directoryprocessor.ImportResult
import com.bca.lol.filesimporter.directoryprocessor.DirectoryProcessor._

class FilesValidator {

  def validateFiles(fileNames: List[String]) = {
    val res = new ImportResult

    for (fileName <- fileNames if !MANDATORY_SALE_FILES.contains(fileName) && !OPTIONAL_SALE_FILES.contains(fileName)) 
      res.addError(fileName + " is wrong")

    for (saleFileName <- MANDATORY_SALE_FILES if !fileNames.contains(saleFileName)) 
      res.addError(saleFileName + " is missing")

    res
  }
}
