package org.repl.poc.lmsdata.controller

import org.repl.poc.lmsdata.dto.{BookDto, IdDto, LibraryBranchDto, ServiceResponse}
import org.repl.poc.lmsdata.service.{BookService, LibraryAdminService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.{PostMapping, RequestBody, RequestMapping, ResponseBody, RestController}

@RestController
@RequestMapping(value = Array("/api"))
class LibraryAdminController @Autowired()(adminService: LibraryAdminService) {
  @PostMapping(value = Array("/v1/admin/library-branch"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody
  def createBranch(@RequestBody input: LibraryBranchDto): ServiceResponse[IdDto] = {
    return adminService.createBranch(input);
  }
}
