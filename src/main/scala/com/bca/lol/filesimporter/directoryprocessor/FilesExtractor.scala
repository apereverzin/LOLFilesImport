package com.bca.lol.filesimporter.directoryprocessor

import java.io.File
import scala.util.{Try, Success, Failure}
import scala.collection.immutable.List

class FilesExtractor {
  def extractFiles(directoryName: String): Try[List[File]] = {
    try {
      Success((new File(directoryName)).listFiles.filter(!_.isDirectory).toList)
    } catch {
      case e: Throwable => Failure(e)
    }
  }
}
