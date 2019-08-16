package org.repl.poc.lmsdata.service

import java.time.LocalDateTime
import java.util.UUID

import org.repl.poc.lmsdata.dto.{IdDto, LibraryBranchDto, ServiceResponse, ServiceResponseError}
import org.repl.poc.lmsdata.mongodb.model.LibraryBranchMdl
import org.repl.poc.lmsdata.mongodb.repository.LibraryBranchRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.collection.mutable

@Service
class LibraryAdminService @Autowired() (libraryBranchRepository: LibraryBranchRepository) {

  def validateCreateInput(input: LibraryBranchDto) :Seq[ServiceResponseError] = {
    val errors: mutable.MutableList[ServiceResponseError] = mutable.MutableList[ServiceResponseError]()
    libraryBranchRepository.findByName(input.name) match {
      case Some(mdl) => {
        errors += ServiceResponseError("BOOK002", "Another branch exists with same name.")
      }
      case None => {
      }
    }
    errors
  }

  def createBranch(input: LibraryBranchDto): ServiceResponse[IdDto] = {
    val response = new ServiceResponse[IdDto]()
    val errors = validateCreateInput(input)
    if (errors.nonEmpty) {
      errors.foreach(error => response.errors += error)
      response.success = false
      return response
    }
    input.createdDate = LocalDateTime.now()
    input.modifiedDate = LocalDateTime.now()
    val model = new LibraryBranchMdl()
    model.populate(input)
    val persistedModel = libraryBranchRepository.save(model)
    val persistedDto = persistedModel.createDto()
    response.success = true
    response.data = Some(IdDto(persistedDto.id))
    response
  }
}
