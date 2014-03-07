package com.bca.lol.filesimporter.persistence

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
import com.bca.lol.filesimporter.data.{ Sale, Lot, LotOption, LotCondition, LotComment }

class FileImportSpringDAO extends SimpleJdbcDaoSupport with FileImportDAO {
  def createSale(sale: Sale): Int = {
    getJdbcTemplate
    sale.id
  }

  def createLot(saleId: Int, lot: Lot): Int = {
    lot.id
  }
  
  def createOption(lotId: Int, option: LotOption): Int = {
    option.id
  }
  
  def createCondition(lotId: Int, condition: LotCondition): Int = {
    condition.id
  }
  
  def createComment(lotId: Int, comment: LotComment): Int = {
    comment.id
  }
}
