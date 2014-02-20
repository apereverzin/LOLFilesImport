package com.bca.lol.filesimporter.validator

import org.scalatest._
import com.bca.lol.filesimporter.directoryprocessor.DirectoryProcessor._

class FilesValidatorTest extends FlatSpec {

  "FileValidator" should "validate one file" in {
    // given
    val filesValidator = new FilesValidator
    val saleFiles = List(CONTROL, SALE, UNIT, LOT, OPTION, CONDS, UCOMMENT)

    // when
    val res = filesValidator.validateFiles(saleFiles)

    // then
    assert(res hasNoErrors)
  }

  "FileValidator" should "not validate one wrong file" in {
    // given
    val filesValidator = new FilesValidator
    val saleFiles = List(CONTROL, SALE, UNIT, LOT, OPTION, CONDS, UCOMMENT, "one")

    // when
    val res = filesValidator.validateFiles(saleFiles)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0) == "one is wrong")
  }

  "FileValidator" should "not validate one missing file" in {
    // given
    val filesValidator = new FilesValidator
    val saleFiles = List(CONTROL, SALE, UNIT, LOT, CONDS, UCOMMENT)

    // when
    val res = filesValidator.validateFiles(saleFiles)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 1)
    assert(res.getErrors(0) == "OPTION is missing")
  }

  "FileValidator" should "not validate two wrong files" in {
    // given
    val filesValidator = new FilesValidator
    val saleFiles = List(CONTROL, SALE, UNIT, LOT, "one", OPTION, CONDS, UCOMMENT, "two")

    // when
    val res = filesValidator.validateFiles(saleFiles)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 2)
  }

  "FileValidator" should "not validate two missing files" in {
    // given
    val filesValidator = new FilesValidator
    val saleFiles = List(CONTROL, UNIT, LOT, CONDS, UCOMMENT)

    // when
    val res = filesValidator.validateFiles(saleFiles)

    // then
    assert(res hasErrors)
    assert(res.getErrors.size == 2)
  }
}
