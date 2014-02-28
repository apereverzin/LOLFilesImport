package com.bca.lol.filesimporter.directoryprocessor

import java.nio.file.Files
import scala.util.{ Try, Success, Failure }

class DirectoryMover {
  def moveDirectory(saleDirectory: SaleDirectory, dest: String, res: ImportResult): Try[Unit] = {
    try {
      val destPath = saleDirectory.directory.getParent.getParent.resolve(dest)
      
      if (!destPath.toFile.exists) {
        destPath.toFile.mkdir
      }
      
      var destDirectory = destPath.resolve(saleDirectory.directory.getFileName)
      
      var i = 0;
      while (destDirectory.toFile.exists) {
        i += 1
        destDirectory = destPath.resolve(saleDirectory.directory.getFileName + "_" + i)
      }
      
      val v = Files.move(saleDirectory.directory, destDirectory)
      
      Success()
    } catch {
      case (e: Throwable) => { e.printStackTrace; Failure(e) }
    }
  }
}
