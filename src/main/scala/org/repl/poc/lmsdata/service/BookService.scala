package org.repl.poc.lmsdata.service

import java.time.LocalDateTime
import java.util.UUID

import org.repl.poc.lmsdata.dto._
import org.repl.poc.lmsdata.mongodb.model.BookMdl
import org.repl.poc.lmsdata.mongodb.repository.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.data.mongodb.core.aggregation.Aggregation._
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.query.Criteria

import scala.collection.JavaConverters._
import scala.collection.mutable

@Service
class BookService @Autowired() (mongoTemplate: MongoTemplate, bookRepository: BookRepository) {
  def getCollection(requestDto: ListViewRequestDto): ServiceResponse[PaginatedListDto[BookDto]] = {
    val response = new ServiceResponse[PaginatedListDto[BookDto]]()
    if (requestDto.pageSize == 0) {
      requestDto.pageSize = 10
    }
    if (requestDto.pageNum == 0) {
      requestDto.pageNum = 1
    }
    val filterCriteria = new Criteria()
    if (requestDto.filters.nonEmpty) {
      requestDto.filters.foreach( entry => {
        filterCriteria.and(entry._1).`is`(requestDto.filters.get(entry._2.toString))
      }
      )
    }
    val agg = newAggregation(
      `match`(filterCriteria),
      skip((requestDto.pageNum - 1) * requestDto.pageSize),
      limit(requestDto.pageSize)
    )
    val results: AggregationResults[BookMdl] = mongoTemplate.aggregate(agg, "Book", classOf[BookMdl])
    val paginatedDto : PaginatedListDto[BookDto] = new PaginatedListDto()
    paginatedDto.results = results.getMappedResults.asScala.map( x => x.createDto() )
    paginatedDto.pageNum = requestDto.pageNum
    paginatedDto.pageSize = requestDto.pageSize
    paginatedDto.totalCount = 0

    response.data = Some(paginatedDto)
    response.success = true
    return response
  }

  def create(input: BookDto): ServiceResponse[IdDto] = {
    val response = new ServiceResponse[IdDto]()
    val errors = validateCreateInput(input)
    if (errors.nonEmpty) {
      errors.foreach(error => response.errors += error)
      response.success = false
      return response
    }
    input.id = UUID.randomUUID().toString()
    input.createdDate = LocalDateTime.now()
    input.modifiedDate = LocalDateTime.now()
    val model = new BookMdl()
    model.populate(input)
    val persistedModel = bookRepository.save(model)
    val persistedDto = persistedModel.createDto()
    response.success = true
    response.data = Some(IdDto(persistedDto.id))
    return response
  }

  def validateCreateInput(input: BookDto): Seq[ServiceResponseError] = {
    val errors: mutable.MutableList[ServiceResponseError] = mutable.MutableList[ServiceResponseError]()
    if (input.title.isEmpty) {
      errors += ServiceResponseError("BOOK001", "title attribute is missing.")
    }
    return errors
  }
}
