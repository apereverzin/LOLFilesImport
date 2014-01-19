package com.bca.lol.filesimporter.directoryprocessor

import java.io.File

class FilesExtractor {
  def extractFiles ( directoryName: String ) = ( new File(directoryName ) ).listFiles.filter ( _.isDirectory ).toList
}
