package com.bca.lol.filesimporter.persistence

import com.bca.lol.filesimporter.data.Sale
import scala.util.{Try, Success, Failure}
import org.springframework.context.support.FileSystemXmlApplicationContext

class PersistenceManager {
  def persistSale(sale: Sale): Try[Unit] = {
    try {
      //val ctx = new FileSystemXmlApplicationContext("./src/main/resources/applicationContext.xml")
      //val dao = ctx.getBean("fileImportDAO").asInstanceOf[FileImportDAO]
      val dao = new FileImportJdbcDAO
      dao.init
      Success()
    } catch {
      case(e: Throwable) => Failure(e)
    }
  }
}