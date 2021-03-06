package org.repl.poc.lmsdata.mongodb.model

import java.time.LocalDateTime

import org.repl.poc.lmsdata.dto.BookDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "Book")
class BookMdl {
  @Id
  var id: String = _
  @Indexed(sparse = true)
  @Field("Book-Title")
  var title: String = _
  @Field("ISBN")
  var isbn: String = _
  @Field("Book-Author")
  var author: String = _
  @Field("Year-Of-Publication")
  var yearOfPublication: String = _
  @Field("Publisher")
  var publisher: String = _
  @Field("Image-URL-S")
  var imageUrlSizeS: String = _
  @Field("Image-URL-M")
  var imageUrlSizeM: String = _
  @Field("Image-URL-L")
  var imageUrlSizeL: String = _

  var createdDate: LocalDateTime = _
  var createdByUID: String = _
  var modifiedDate: LocalDateTime = _
  var modifiedByUID: String = _

  def createDto(): BookDto = {
    val retDto = new BookDto()
    retDto.id = id
    retDto.title = title
    retDto.isbn = isbn
    retDto.author = author
    retDto.yearOfPublication = yearOfPublication
    retDto.publisher = publisher
    retDto.imageUrlSizeS = imageUrlSizeS
    retDto.imageUrlSizeM = imageUrlSizeM
    retDto.imageUrlSizeL = imageUrlSizeL
    retDto.createdDate = createdDate
    retDto.createdByUID = createdByUID
    retDto.modifiedDate = modifiedDate
    retDto.modifiedByUID = modifiedByUID
    return retDto
  }

  def populate(input: BookDto) = {
    this.id = input.id
    this.title = input.title
    this.isbn = input.isbn
    this.author= input.author
    this.yearOfPublication = input.yearOfPublication
    this.publisher = input.publisher
    this.imageUrlSizeS = input.imageUrlSizeS
    this.imageUrlSizeM = input.imageUrlSizeM
    this.imageUrlSizeL = input.imageUrlSizeL
    this.createdDate = input.createdDate
    this.createdByUID = input.createdByUID
    this.modifiedDate = input.modifiedDate
    this.modifiedByUID = input.modifiedByUID
  }
}
