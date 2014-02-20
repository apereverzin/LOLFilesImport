package com.bca.lol.filesimporter.directoryprocessor

import akka.actor.{ Actor, ActorLogging, ActorSystem, Props }
import akka.actor.ActorRef

class FileImporter {
  
  def processDirectory(languageId: Int, directoryName: String) = {
    val system = ActorSystem("FileImporter")
    val dispatcher = system.actorOf(Props[DirectoryProcessorDispatcher], "Dispatcher")
    dispatcher ! Directory(languageId, directoryName)
  }
}
