package com.bca.lol.filesimporter.persistence

import com.bca.lol.filesimporter.data.Sale
import scala.util.{Try, Success, Failure}

class PersistenceManager {
  def persistSale(sale: Sale): Try[Unit] = {
    try {
      Success()
    } catch {
      case(e: Throwable) => Failure(e)
    }
  }
}