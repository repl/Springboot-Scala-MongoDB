package org.repl.poc.lmsdata.dto

case class UserDto(username: String) extends Item {
  var firstName: String = _
  var lastName: String = _

  var city: String = _
  var state: String = _
  var country: String = _
  var categories: List[String] = _
}
