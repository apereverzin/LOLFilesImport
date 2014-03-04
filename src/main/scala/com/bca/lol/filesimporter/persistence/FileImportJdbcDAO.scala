package com.bca.lol.filesimporter.persistence

import com.bca.lol.filesimporter.data.{ Sale, Lot, Option, Condition, Comment }
import java.sql.{ DriverManager, Connection }

class FileImportJdbcDAO extends FileImportDAO {
  var conn: Connection = _
  
  val CREATE_SALE = "INSERT INTO SALE (contextId, name, description, auctionend, auctionstart, status, accesscontrol, locked, created, " +
                                      "catalogtype, extended, statustext, reference, lastmodified, ims, defaultlanguage, documentlanguage) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
  val CREATE_LOT = "INSERT INTO LOT (contextId, vendor, title, link, image, description, bid, bidCount, reserve, minimumBid, price, currency, " +
                                    "category, template, flags, status, quantity, locked, created, lotType, displayId, saleCode, saleId) " + 
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
  val CREATE_OPTION = "INSERT INTO OPTION () VALUES ()"
  val CREATE_CONDITION = ""
  
  def init = {
    Class.forName("com.mysql.jdbc.Driver")
    conn = DriverManager.getConnection("jdbc:mysql://localhost/loldb", "root", null)
    val st = conn.prepareStatement("select count(*) from sale")
    val rs = st.executeQuery()
    rs.next()
    val cnt = rs.getInt(1)
    println(s"-----------${cnt}")
  }

  def createSale(sale: Sale): Sale = {
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
