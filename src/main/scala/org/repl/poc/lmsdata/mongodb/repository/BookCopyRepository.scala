package org.repl.poc.lmsdata.mongodb.repository

import org.repl.poc.lmsdata.mongodb.model.{BookCopyMdl, BookMdl}
import org.springframework.data.repository.Repository

trait BookCopyRepository extends Repository[BookCopyMdl, String] {
  def findByBook(book: BookMdl): List[BookCopyMdl]
  def save(model: BookCopyMdl): BookCopyMdl
  def findById(id: String): Option[BookCopyMdl]
}
