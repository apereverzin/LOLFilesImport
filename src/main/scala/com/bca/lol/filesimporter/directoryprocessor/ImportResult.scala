package com.bca.lol.filesimporter.directoryprocessor

import scala.collection.immutable.List

case class ImportResult() {
  var errors: List[String] = List[String]();
  
  def addError(err: String) = errors = err :: errors
  
  def hasNoErrors: Boolean = errors.size == 0
  
  def hasErrors: Boolean = errors.size > 0
  
  def getErrors: List[String] = errors
}