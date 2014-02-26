package com.bca.lol.filesimporter.directoryprocessor

import java.io.File
import scala.util.{Try, Success, Failure}
import scala.collection.immutable.List

class FilesExtractor {
  def extractFiles(directory: File): Try[List[File]] = {
    try {
      Success(directory.listFiles.filter(!_.isDirectory).toList)
    } catch {
      case e: Throwable => Failure(e)
    }
  }
}
