package com.bca.lol.filesimporter

object FilesImporter extends App {
  def service = new FileWatchingService()
  service.watchDirectory(1, "/private/etc/Work/scalaprojects/LOLFilesImport/import")
}