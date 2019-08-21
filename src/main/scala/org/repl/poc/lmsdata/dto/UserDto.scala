package org.repl.poc.lmsdata.dto

case class UserDto(username: String) extends Item {
  var city: String = _
  var state: String = _
  var country: String = _
  var categories: List[String] = _
}
