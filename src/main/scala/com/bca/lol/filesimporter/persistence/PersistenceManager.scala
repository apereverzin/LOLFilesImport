package com.bca.lol.filesimporter.persistence

import com.bca.lol.filesimporter.data.Sale
import scala.util.{Try, Success, Failure}
import org.springframework.context.support.FileSystemXmlApplicationContext
import com.bca.lol.filesimporter.data.Lot

class PersistenceManager {
  def persistSale(sale: Sale): Try[Unit] = {
    try {
      //val ctx = new FileSystemXmlApplicationContext("./src/main/resources/applicationContext.xml")
      //val dao = ctx.getBean("fileImportDAO").asInstanceOf[FileImportDAO]
      val dao = new FileImportJdbcDAO
      dao.init
      
      val saleId = dao.createSale(sale)
      
      sale.lots.foreach(createLot(dao, saleId, _))
      
      Success()
    } catch {
      case(e: Throwable) => Failure(e)
    }
  }
  
  def createLot(dao: FileImportDAO, saleId: Int, lot: Lot) = {
    val lotId = dao.createLot(saleId, lot)
    lot.options.foreach(dao.createOption(lotId, _))
    lot.conditions.foreach(dao.createCondition(lotId, _))
    lot.comments.foreach(dao.createComment(lotId, _))
  }
}