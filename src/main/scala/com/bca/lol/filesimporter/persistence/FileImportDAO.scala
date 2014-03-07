package com.bca.lol.filesimporter.persistence

import com.bca.lol.filesimporter.data.{ Sale, Lot, LotOption, LotCondition, LotComment }

trait FileImportDAO {
  def createSale(sale: Sale): Int
  def createLot(saleId: Int, lot: Lot): Int
  def createOption(lotId: Int, option: LotOption): Int
  def createCondition(lotId: Int, condition: LotCondition): Int
  def createComment(lotId: Int, comment: LotComment): Int
}
