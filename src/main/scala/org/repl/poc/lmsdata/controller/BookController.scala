package org.repl.poc.lmsdata.controller

import org.apache.commons.lang3.StringUtils
import org.repl.poc.lmsdata.dto._
import org.repl.poc.lmsdata.exception.ServiceException
import org.repl.poc.lmsdata.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/api"))
class BookController @Autowired()(bookService: BookService) {

  @RequestMapping(value = Array("/v1/books"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getBooks(@RequestParam(value = "pageSize", required = false) pageSize : String,
               @RequestParam(value = "pageNum", required = false) pageNum : String,
               @RequestParam(value = "sortKey", required = false) sortKey : String,
               @RequestParam(value = "sortOrder", required = false, defaultValue = "asc") sortOrder : String,
               @RequestParam(value = "rangeKey", required = false) filterRangeKey : String,
               @RequestParam(value = "rangeOp", required = false) filterRangeOp : String,
               @RequestParam(value = "rangeValue", required = false) filterRangeValue : String): ServiceResponse[PaginatedListDto[BookDto]] = {
    val requestDto = new ListViewRequestDto()
    val filtersMap = requestDto.filters
    if (!StringUtils.isEmpty(filterRangeKey)) {
      filtersMap.put("rangeKey", filterRangeKey.toString());
    }
    if (!StringUtils.isEmpty(filterRangeOp)) {
      filtersMap.put("rangeOp", filterRangeOp.toString())
    }
    if (!StringUtils.isEmpty(filterRangeValue)) {
      filtersMap.put("rangeValue", filterRangeValue.toString())
    }
    if (!StringUtils.isEmpty(pageSize)) {
      try {
        requestDto.pageSize = Integer.parseInt(pageSize)
      } catch {
        case ex: NumberFormatException => throw ServiceException("Not able to parse pageSize value.")
      }
    }
    if (!StringUtils.isEmpty(pageNum)) {
      try {
        requestDto.pageNum = Integer.parseInt(pageNum)
      } catch {
        case ex: NumberFormatException => throw ServiceException("Not able to parse pageNum value.")
      }
    }
    if (!StringUtils.isEmpty(sortKey)) {
      if (!StringUtils.isEmpty(sortOrder)) {
        requestDto.sortMap.put(sortKey.toString(), sortOrder)
      } else {
        requestDto.sortMap.put(sortKey.toString(), "asc")
      }
    }
    return bookService.getCollection(requestDto)
  }

  @PostMapping(value = Array("/v1/books"), consumes = Array(MediaType.APPLICATION_JSON_VALUE), produces = Array(MediaType.APPLICATION_JSON_VALUE))
  @ResponseBody
  def createUsers(@RequestBody input: BookDto): ServiceResponse[IdDto] = {
    return bookService.create(input)
  }
}
