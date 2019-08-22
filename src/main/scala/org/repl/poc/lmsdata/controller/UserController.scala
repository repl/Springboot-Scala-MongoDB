package org.repl.poc.lmsdata.controller

import java.security.Principal

import org.repl.poc.lmsdata.dto.{IdDto, ServiceResponse, UserCreateDto, UserDto}
import org.repl.poc.lmsdata.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/api"))
class UserController @Autowired()(userService: UserService) {
  private val logger = LoggerFactory.getLogger(classOf[UserController])

  @PostMapping(value = Array("/v1/user"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody
  def createUser(@RequestBody input: UserCreateDto): ServiceResponse[IdDto] = {
    return userService.createUser(input);
  }

  @GetMapping(value = Array("/v1/user/profile"))
  @PreAuthorize("authenticated")
  def getLoggedInProfile(auth:Authentication, user:Principal): ServiceResponse[UserDto] = {
    logger.debug("user {}", user)
    logger.debug("auth {}", auth)
    return new ServiceResponse[UserDto] //userService.getProfile(auth.)
  }

  @GetMapping(value = Array("/v1/user/{id}"))
  def getProfile(@PathVariable("id") id: String): ServiceResponse[UserDto] = {
    userService.getUser(id)
  }
}
