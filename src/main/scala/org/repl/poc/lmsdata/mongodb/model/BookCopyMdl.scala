package org.repl.poc.lmsdata.mongodb.model

import java.time.LocalDateTime

import org.repl.poc.lmsdata.dto.{BookCopyDto, BookDto}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{DBRef, Document, Field}

@Document(collection = "BookCopy")
class BookCopyMdl {
  @Id
  var id: String = _
  @Indexed(sparse = true)
  @DBRef
  var book: BookMdl = _
  @Indexed(sparse = true)
  @DBRef
  var branch: LibraryBranchMdl = _

  var createdDate: LocalDateTime = _
  var createdByUID: String = _
  var modifiedDate: LocalDateTime = _
  var modifiedByUID: String = _

  def createDto(): BookCopyDto = {
    val retDto = new BookCopyDto()
    retDto.id = id
    retDto.book = book.createDto()
    retDto.branch = branch.createDto()
    retDto.createdDate = createdDate
    retDto.createdByUID = createdByUID
    retDto.modifiedDate = modifiedDate
    retDto.modifiedByUID = modifiedByUID
    return retDto
  }

  def populate(input: BookCopyDto) = {
    this.id = input.id
    this.book = new BookMdl()
    this.book.id = input.book.id
    this.branch = new LibraryBranchMdl()
    this.branch.id = input.branch.id
    this.createdDate = input.createdDate
    this.createdByUID = input.createdByUID
    this.modifiedDate = input.modifiedDate
    this.modifiedByUID = input.modifiedByUID
  }
}
