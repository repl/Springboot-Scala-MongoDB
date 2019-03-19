package org.repl.poc.lmsdata.mongodb.repository

import org.repl.poc.lmsdata.mongodb.model.BookMdl
import org.springframework.data.repository.Repository

trait BookRepository extends Repository[BookMdl, String] {
  def save(model: BookMdl): BookMdl
}
