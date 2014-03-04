package com.bca.lol.filesimporter.persistence

import com.bca.lol.filesimporter.data.{ Sale, Lot, Option, Condition, Comment }

trait FileImportDAO {
  def createSale(sale: Sale): Sale
  def createLot(sale: Sale, lot: Lot): Lot
  def createOption(lot: Lot, option: Option): Option
  def createCondition(lot: Lot, condition: Condition): Condition
  def createComment(lot: Lot, comment: Comment): Comment
}