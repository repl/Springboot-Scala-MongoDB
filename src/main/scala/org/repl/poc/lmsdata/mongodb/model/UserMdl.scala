package org.repl.poc.lmsdata.mongodb.model

import java.time.LocalDateTime

import com.fasterxml.jackson.annotation.JsonIgnore
import org.repl.poc.lmsdata.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.{Document, Field}

@Document(collection = "User")
class UserMdl {
  @Id
  var id: String = _
  @Indexed
  var username: String = _
  @JsonIgnore
  var password: String = _
  var usernum: Long = _
  @Field("firstname")
  var firstName: String = _
  @Field("lastname")
  var lastName: String = _
  var city: String = _
  var state: String = _
  var country: String = _
  var categories: List[String] = _
  var roles: List[String] = _

  var createdDate: LocalDateTime = _
  var createdByUID: String = _
  var modifiedDate: LocalDateTime = _
  var modifiedByUID: String = _

  def createDto(): UserDto = {
    val retDto = UserDto(username)
    retDto.id = id
    retDto.firstName = firstName
    retDto.lastName = lastName
    retDto.city = city
    retDto.state = state
    retDto.country = country
    retDto.createdDate = createdDate
    retDto.createdByUID = createdByUID
    retDto.modifiedDate = modifiedDate
    retDto.modifiedByUID = modifiedByUID
    return retDto
  }
}
