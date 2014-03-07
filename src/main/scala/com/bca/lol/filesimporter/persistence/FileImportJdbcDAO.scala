package com.bca.lol.filesimporter.persistence

import com.bca.lol.filesimporter.data.{ Sale, Lot, LotOption, LotCondition, LotComment }
import java.sql.{ DriverManager, Connection }
import java.sql.PreparedStatement
import java.sql.ResultSet
import com.bca.lol.filesimporter.data.Identifiable

object FileImportJdbcDAO {
  
}

class FileImportJdbcDAO extends FileImportDAO {
  var conn: Connection = _
  
  val CREATE_SALE = "INSERT INTO SALE (context_id, name, description, auction_end, auction_start, status, access_control, locked, created, " +
                                      "catalog_type, extended, status_text, reference, last_modified, ims, default_language, document_language) " + 
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
  val CREATE_LOT = "INSERT INTO LOT (display_sequence, vendor, title, link, image, description, bid, bid_count, reserve, minimum_bid, price, currency, " +
                                    "category, template, flags, status, quantity, locked, lot_type, display_id, sale_code, sale_id) " + 
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
  val CREATE_OPTION = "INSERT INTO LOT_OPTION (display_sequence, description, lot_id) VALUES (?, ?, ?)"
  val CREATE_CONDITION = "INSERT INTO LOT_CONDITION (display_sequence, description, status, lot_id) VALUES (?, ?, ?, ?)"
  val CREATE_COMMENT = "INSERT INTO LOT_COMMENT (display_sequence, comment_type, comment, lot_id) VALUES (?, ?, ?, ?)"
    
  val GET_SALE_ID = "SELECT MAX(ID) FROM SALE";
  val GET_LOT_ID = "SELECT MAX(ID) FROM LOT";
  val GET_OPTION_ID = "SELECT MAX(ID) FROM LOT_OPTION";
  val GET_CONDITION_ID = "SELECT MAX(ID) FROM LOT_CONDITION";
  val GET_COMMENT_ID = "SELECT MAX(ID) FROM LOT_COMMENT";
  
  def init = {
    Class.forName("com.mysql.jdbc.Driver")
    conn = DriverManager.getConnection("jdbc:mysql://localhost/loldb", "root", null)
  }

  def createSale(sale: Sale): Int = {
    val st = conn.prepareStatement(CREATE_SALE)
    st.setInt(1, sale.contextId)
    st.setString(2, sale.name)
    st.setString(3, sale.description)
    st.setDate(4, sale.auctionEnd)
    st.setDate(5, sale.auctionStart)
    st.setInt(6, sale.status)
    st.setInt(7, sale.accessControl)
    st.setInt(8, sale.locked)
    st.setDate(9, sale.created)
    st.setInt(10, sale.catalogType)
    st.setString(11, sale.extended)
    st.setString(12, sale.statusText)
    st.setString(13, sale.reference)
    st.setDate(14, sale.lastModified)
    st.setInt(15, sale.ims)
    st.setString(16, sale.defaultLanguage)
    st.setString(17, sale.documentLanguage)
    st.execute
    sale.id = getId(GET_SALE_ID)
    sale.id
  }

  def createLot(saleId: Int, lot: Lot): Int = {
    createEntity(createLotEntity, saleId, lot, CREATE_LOT, GET_LOT_ID)
  }
  
  def createOption(lotId: Int, option: LotOption): Int = {
    createEntity(createOptionEntity, lotId, option, CREATE_OPTION, GET_OPTION_ID)
  }
  
  def createCondition(lotId: Int, condition: LotCondition): Int = {
    createEntity(createConditionEntity, lotId, condition, CREATE_CONDITION, GET_CONDITION_ID)
  }
  
  def createComment(lotId: Int, comment: LotComment): Int = {
    createEntity(createCommentEntity, lotId, comment, CREATE_COMMENT, GET_COMMENT_ID)
  }
  
  private def createLotEntity(st: PreparedStatement, saleId: Int, lot: Lot): Unit = {
    st.setInt(1, lot.displaySequence)
    st.setInt(2, lot.vendor)
    st.setString(3, lot.title)
    st.setString(4, lot.link)
    st.setString(5, lot.image)
    st.setString(6, lot.description)
    st.setInt(7, lot.bid)
    st.setInt(8, lot.bidCount)
    st.setInt(9, lot.reserve)
    st.setInt(10, lot.minimumBid)
    st.setInt(11, lot.price)
    st.setString(12, lot.currency)
    st.setInt(13, lot.category)
    st.setInt(14, lot.template)
    st.setInt(15, lot.flags)
    st.setInt(16, lot.status)
    st.setInt(17, lot.quantity)
    st.setInt(18, lot.locked)
    st.setInt(19, lot.lotType)
    st.setString(20, lot.displayId)
    st.setString(21, lot.saleCode)
    st.setInt(22, saleId)
  }
  
  private def createOptionEntity(st: PreparedStatement, lotId: Int, option: LotOption): Unit = {
    st.setInt(1, option.displaySequence)
    st.setString(2, option.description)
    st.setInt(3, lotId)
  }
  
  private def createConditionEntity(st: PreparedStatement, lotId: Int, condition: LotCondition): Unit = {
    st.setInt(1, condition.displaySequence)
    st.setString(2, condition.description)
    st.setString(3, condition.status)
    st.setInt(4, lotId)
  }
  
  private def createCommentEntity(st: PreparedStatement, lotId: Int, comment: LotComment): Unit = {
    st.setInt(1, comment.displaySequence)
    st.setString(2, comment.commentType)
    st.setString(3, comment.comment)
    st.setInt(4, lotId)
  }
  
  private def createEntity [T <: Identifiable] (f: (PreparedStatement, Int, T) => Unit, id: Int, entity: T, 
      statement: String, getIdStatement: String): Int = {
    var st = None: Option[PreparedStatement]
    
    try {
      st = Some(conn.prepareStatement(statement))
      f(st.get, id, entity)
      st.get.execute
      getId(getIdStatement)
    } finally {
      if(st.isDefined) st.get.close
    }
  }
  
  private def getId(stmt: String): Int = {
    var idSt = None: Option[PreparedStatement]
    var rs = None: Option[ResultSet]
    try {
      idSt = Some(conn.prepareStatement(stmt))
      rs = Some(idSt.get.executeQuery)
      rs.get.next
      val res = rs.get.getInt(1)
      res
    } finally {
      if(idSt.isDefined) idSt.get.close
      if(rs.isDefined) rs.get.close
    }
  }
}
