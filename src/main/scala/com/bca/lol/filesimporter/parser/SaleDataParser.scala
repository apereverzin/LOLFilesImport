package com.bca.lol.filesimporter.parser

import com.bca.lol.filesimporter.filedata.SaleData

class SaleDataParser extends FileLineParser {

  override def parseLine ( line: String ) = {
    val saleData = new SaleData
    
    saleData.saleNumber = takeFirstField ( line, 6 )
    saleData.saleCode = takeNextField ( line, 3 )
    saleData.saleDescription = takeNextField ( line, 25 )
    saleData.userGroupCode = takeNextField ( line, 4 )
    saleData.saleDate = takeNextField ( line, 7 )
    saleData.saleTime = takeNextField ( line, 6 )
    saleData.saleVersion = takeNextField ( line, 3 )
    
    saleData
  }
}
