package com.bca.lol.filesimporter.directoryprocessor

import akka.actor.{ Actor, ActorLogging, ActorSystem, Props }
import akka.actor.ActorRef
import java.nio.file.Path

class FileImporter {
  
  def processDirectory(languageId: Int, directory: Path) = {
    val system = ActorSystem("FileImporter")
    val dispatcher = system.actorOf(Props[DirectoryProcessorDispatcher], "Dispatcher")
    dispatcher ! SaleDirectory(languageId, directory)
  }
}
