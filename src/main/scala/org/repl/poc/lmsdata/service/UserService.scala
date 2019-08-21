package org.repl.poc.lmsdata.service

import java.time.LocalDateTime

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util

import org.repl.poc.lmsdata.dto.{IdDto, LibraryBranchDto, ServiceResponse, ServiceResponseError, UserCreateDto, UserDto}
import org.repl.poc.lmsdata.mongodb.model.UserMdl
import org.repl.poc.lmsdata.mongodb.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.{UserDetailsService, UsernameNotFoundException}
import org.springframework.security.crypto.password.PasswordEncoder

import scala.collection.mutable

@Service(value = "userService")
class UserService extends UserDetailsService {
  @Autowired
  private val userRepository: UserRepository = null
  @Autowired private
  val passwordEncoder: PasswordEncoder = null

  @throws[UsernameNotFoundException]
  def loadUserByUsername(userId: String): org.springframework.security.core.userdetails.User = {
    val user = userRepository.findByUsername(userId)
    if (user == null) throw new UsernameNotFoundException("Invalid username or password.")
    new org.springframework.security.core.userdetails.User(user.username, user.password, getAuthority)
  }

  private def getAuthority = util.Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))

  def findAll: util.List[UserDto] = {
    val list = new util.ArrayList[UserDto]
    userRepository.findAll.iterator.forEachRemaining(mdl => {
      list.add(mdl.createDto())
    })
    list
  }

  def createUser(input: UserCreateDto): ServiceResponse[IdDto] = {
    val response = new ServiceResponse[IdDto]()
    val errors = validateCreateInput(input)
    if (errors.nonEmpty) {
      errors.foreach(error => response.errors += error)
      response.success = false
      return response
    }
    val model = new UserMdl()
    model.username = input.username
    model.password = passwordEncoder.encode(input.password)
    model.createdDate = LocalDateTime.now()
    model.modifiedDate = LocalDateTime.now()
    val persistedModel = userRepository.save(model)
    val persistedDto = persistedModel.createDto()
    response.success = true
    response.data = Some(IdDto(persistedDto.id))
    response
  }

  def validateCreateInput(input: UserCreateDto) :Seq[ServiceResponseError] = {
    val errors: mutable.MutableList[ServiceResponseError] = mutable.MutableList[ServiceResponseError]()
    val existingUser = userRepository.findByUsername(input.username)
    if (existingUser != null) {
      errors += ServiceResponseError("USER002", "Another user exists with same name.")
    }
    errors
  }
}
