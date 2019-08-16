package org.repl.poc.lmsdata.mongodb.model

import java.time.LocalDateTime

import org.repl.poc.lmsdata.dto.{BookDto, LibraryBranchDto}
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "LibraryBranch")
class LibraryBranchMdl {
  @Id
  var id: String = _

  var name: String = _

  var city: String = _

  var createdDate: LocalDateTime = _
  var createdByUID: String = _
  var modifiedDate: LocalDateTime = _
  var modifiedByUID: String = _

  def createDto(): LibraryBranchDto = {
    val retDto = LibraryBranchDto(name, city)
    retDto.id = id
    retDto.createdDate = createdDate
    retDto.createdByUID = createdByUID
    retDto.modifiedDate = modifiedDate
    retDto.modifiedByUID = modifiedByUID
    return retDto
  }

  def populate(input: LibraryBranchDto) = {
    this.id = input.id
    this.name = input.name
    this.city = input.city
    this.createdDate = input.createdDate
    this.createdByUID = input.createdByUID
    this.modifiedDate = input.modifiedDate
    this.modifiedByUID = input.modifiedByUID
  }
}
