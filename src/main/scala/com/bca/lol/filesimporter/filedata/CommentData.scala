package com.bca.lol.filesimporter.filedata

sealed class CommentData extends FileData {
  var unitSurrogate: String = _
  var commentType: String = _
  var sequenceNumber: String = _
  var comment: String = _
}
