package com.bca.lol.filesimporter.directoryprocessor

import akka.actor.{ Actor, ActorLogging, Props }

class DirectoryProcessorActor extends Actor with ActorLogging {
  val directoryProcessor = new DirectoryProcessor

  def receive = {
    case dir: SaleDirectory => sender ! (dir, directoryProcessor.process(dir))
  }

}
