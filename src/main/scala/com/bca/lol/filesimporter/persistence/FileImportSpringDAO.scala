package com.bca.lol.filesimporter.persistence

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport
import com.bca.lol.filesimporter.data.{ Sale, Lot, Option, Condition, Comment }

class FileImportSpringDAO extends SimpleJdbcDaoSupport with FileImportDAO {
  def createSale(sale: Sale): Sale = {
    getJdbcTemplate
    sale
  }

  def createLot(sale: Sale, lot: Lot): Lot = {
    lot
  }
  
  def createOption(lot: Lot, option: Option): Option = {
    option
  }
  
  def createCondition(lot: Lot, condition: Condition): Condition = {
    condition
  }
  
  def createComment(lot: Lot, comment: Comment): Comment = {
    comment
  }
}
