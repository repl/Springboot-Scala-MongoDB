package org.repl.poc.lmsdata.controller

import org.repl.poc.lmsdata.dto.{IdDto, ServiceResponse, UserCreateDto}
import org.repl.poc.lmsdata.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/api"))
class UserController @Autowired()(userService: UserService) {
  @PostMapping(value = Array("/v1/user"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody
  def createUser(@RequestBody input: UserCreateDto): ServiceResponse[IdDto] = {
    return userService.createUser(input);
  }
}
