package com.bca.lol.filesimporter.filedata

sealed class ControlData ( sn : Int, un : Int, ln : Int, on : Int, condn : Int, commn : Int ) extends FileData {
  
  def this() =  this( 0, 0, 0, 0, 0, 0 )
  
  var salesNumber : Int = sn
  var unitsNumber : Int = un
  var lotsNumber : Int = ln
  var optionsNumber : Int = on
  var conditionsNumber : Int = condn
  var commentsNumber : Int = commn
}
