package org.repl.poc.lmsdata.model

import java.time.LocalDateTime

import org.repl.poc.lmsdata.dto.BookDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "User")
class BookMdl {
  @Id
  var id: String = _
  @Indexed(sparse = true)
  var name: String = _

  var createdDate: LocalDateTime = _
  var createdByUID: String = _
  var modifiedDate: LocalDateTime = _
  var modifiedByUID: String = _

  def createDto(): BookDto = {
    val retDto = new BookDto()
    retDto.id = id
    retDto.name = name
    retDto.createdDate = createdDate
    retDto.createdByUID = createdByUID
    retDto.modifiedDate = modifiedDate
    retDto.modifiedByUID = modifiedByUID
    return retDto
  }
}
