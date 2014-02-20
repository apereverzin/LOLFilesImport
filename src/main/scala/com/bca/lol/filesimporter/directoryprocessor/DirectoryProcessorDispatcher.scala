package com.bca.lol.filesimporter.directoryprocessor

import akka.actor.{ Actor, ActorLogging, Props }

class DirectoryProcessorDispatcher extends Actor {
  def receive = {
    case dir: Directory => processDirectory(dir)
    case res: (Directory, ImportResult) => processResult(res)
  }

  private def processDirectory(dir: Directory) = {
    val directoryProcessor = context.actorOf(Props[DirectoryProcessor], "DirectoryProcessor")
    directoryProcessor ! dir
  }

  private def processResult(res: (Directory, ImportResult)) = {
    if (res._2.hasNoErrors) println(res._1.directoryName + " processed successfully")
    else processFailure(res)
  }

  private def processFailure(res: (Directory, ImportResult)) = {
    println("Processing " + res._1.directoryName + " failed")
    res._2.getErrors.foreach(println(_))
  }
}
