package org.repl.poc.lmsdata.mongodb.model

import java.time.LocalDateTime

import org.bson.types.ObjectId
import org.repl.poc.lmsdata.dto.UserBookRatingDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{Document, Field}

@Document(collection = "UserBookRating")
class UserBookRatingMdl {
  @Id
  var id: String = _
  @Indexed
  @Field("ISBN")
  var isbn: String = _
  var rating: Integer = _
  @Indexed
  var userId: String = _
  var firstname: String = _
  var lastname: String = _
  var uid: ObjectId = _

  var createdDate: LocalDateTime = _
  var createdByUID: String = _
  var modifiedDate: LocalDateTime = _
  var modifiedByUID: String = _

  def createDto(): UserBookRatingDto = {
    UserBookRatingDto(userId, isbn, rating, firstname, lastname)
  }
}
