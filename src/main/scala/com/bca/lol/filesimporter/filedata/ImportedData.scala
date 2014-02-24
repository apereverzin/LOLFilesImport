package com.bca.lol.filesimporter.filedata

case class ImportedData(
  val control: ControlData,
  val sales: List[SaleData],
  val units: List[UnitData],
  val lots: List[LotData],
  val options: List[OptionData],
  val conditions: List[ConditionData],
  val comments: List[CommentData])
