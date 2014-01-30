package com.bca.lol.filesimporter.filedata

case class SaleData( 
  saleNumber: String = "",
  saleCode: String = "",
  saleDescription: String = "",
  userGroupCode: String = "",
  saleDate: String = "",
  saleTime: String = "",
  saleVersion: String = "") extends FileData
