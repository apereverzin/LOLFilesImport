package com.bca.lol.filesimporter.filedata

case class CommentData extends FileData { 
  var commentType: String = _
  var sequenceNumber: String = _
  var comment: String = _
}
