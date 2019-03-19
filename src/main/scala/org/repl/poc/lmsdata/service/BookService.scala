package org.repl.poc.lmsdata.service

import org.repl.poc.lmsdata.dto.{BookDto, ListViewRequestDto, PaginatedListDto, ServiceResponse}
import org.repl.poc.lmsdata.model.BookMdl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.data.mongodb.core.aggregation.Aggregation._
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.query.Criteria

import scala.collection.JavaConverters._

@Service
class BookService @Autowired() ( mongoTemplate: MongoTemplate) {
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
}
