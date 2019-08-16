package org.repl.poc.lmsdata.mongodb.repository

import org.bson.types.ObjectId
import org.repl.poc.lmsdata.mongodb.model.{BookCopyMdl, BookMdl}
import org.springframework.data.mongodb.repository.{MongoRepository, Query}

trait BookCopyRepository extends MongoRepository[BookCopyMdl, String] {
  def save(model: BookCopyMdl): BookCopyMdl
  def findByBook(book: BookMdl): List[BookCopyMdl]
  @Query(value="{ 'book.$id' : ?0 }")
  def findByBookId(bookId: ObjectId): java.util.List[BookCopyMdl]
}
