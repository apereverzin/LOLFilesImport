package com.bca.lol.filesimporter.directoryprocessor

import akka.actor.{ Actor, ActorLogging, Props }

class DirectoryProcessorDispatcher extends Actor {
  def receive = {
    case dir: SaleDirectory => processDirectory(dir)
    case res: (SaleDirectory, ImportResult) => processResult(res)
  }

  private def processDirectory(dir: SaleDirectory) = {
    val directoryProcessor = context.actorOf(Props[DirectoryProcessorActor], "DirectoryProcessor")
    directoryProcessor ! dir
  }

  private def processResult(res: (SaleDirectory, ImportResult)) = {
    if (res._2.hasNoErrors) println(res._1.directory.getFileName() + " processed successfully")
    else processFailure(res)
  }

  private def processFailure(res: (SaleDirectory, ImportResult)) = {
    println("Processing " + res._1.directory.getFileName() + " failed")
    res._2.getErrors.foreach(println(_))
  }
}
