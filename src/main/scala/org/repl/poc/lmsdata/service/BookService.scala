package org.repl.poc.lmsdata.service

import java.time.LocalDateTime

import org.bson.types.ObjectId
import org.repl.poc.lmsdata.dto._
import org.repl.poc.lmsdata.mongodb.model.{BookCopyMdl, BookMdl}
import org.repl.poc.lmsdata.mongodb.repository.{BookCopyRepository, BookRepository, LibraryBranchRepository}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.data.mongodb.core.aggregation.Aggregation._
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.query.Criteria

import scala.collection.JavaConverters._
import scala.collection.mutable

@Service
class BookService @Autowired() (mongoTemplate: MongoTemplate,
                                bookRepository: BookRepository,
                                libraryBranchRepository: LibraryBranchRepository,
                                bookCopyRepository: BookCopyRepository) {

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
        if (entry._1.toString().equalsIgnoreCase("title")) {
          filterCriteria.and("Book-Title").`regex`(entry._2.toString())
        } else if (entry._1.toString().equalsIgnoreCase("author")) {
          filterCriteria.and("Book-Author").`regex`(entry._2.toString())
        } else if (entry._1.toString().equalsIgnoreCase("isbn")) {
          filterCriteria.and("ISBN").`regex`(entry._2.toString())
        } else {
          throw new RuntimeException("Invalid searchKey");
        }
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
    input.createdDate = LocalDateTime.now()
    input.modifiedDate = LocalDateTime.now()
    val model = new BookMdl()
    model.populate(input)
    val persistedModel = bookRepository.save(model)
    val persistedDto = persistedModel.createDto()
    response.success = true
    response.data = Some(IdDto(persistedDto.id))
    response
  }

  def validateCreateInput(input: BookDto): Seq[ServiceResponseError] = {
    val errors: mutable.MutableList[ServiceResponseError] = mutable.MutableList[ServiceResponseError]()
    if (input.title.isEmpty) {
      errors += ServiceResponseError("BOOK001", "title attribute is missing.")
    }
    errors
  }

  def get(id: String): ServiceResponse[BookDto] = {
    val response = new ServiceResponse[BookDto]()
    bookRepository.findById(id) match {
      case Some(mdl) => {
        response.success = true
        response.data = Option(mdl.createDto())
      }
      case None => {
        response.success = false
        response.errors.+("No book found!")
      }
    }
    return response
  }

  def createCopy(input: BookCopyCreateDto): ServiceResponse[IdDto] = {
    val response = new ServiceResponse[IdDto]()
    var bookCopyDto = new BookCopyDto()
    bookRepository.findById(input.bookId) match {
      case Some(mdl) => {
        bookCopyDto.book = mdl.createDto()
      }
      case None => {
        response.success = false
        response.errors.+("No book found!")
      }
    }
    libraryBranchRepository.findById(input.branchId) match {
      case Some(mdl) => {
        bookCopyDto.branch = mdl.createDto()
      }
      case None => {
        response.success = false
        response.errors.+("No branch found!")
      }
    }
    if (response.errors.size == 0) {
      val bookCopyMdl = new BookCopyMdl()
      bookCopyMdl.populate(bookCopyDto)
      val persistedModel = bookCopyRepository.save(bookCopyMdl)
      val persistedDto = persistedModel.createDto()
      response.success = true
      response.data = Some(IdDto(persistedDto.id))
    }
    response
  }

  def getBranchesWithBook(bookId: String): ServiceResponse[List[LibraryBranchDto]] = {
    val response = new ServiceResponse[List[LibraryBranchDto]]()
    bookRepository.findById(bookId) match {
      case Some(mdl) => {
        val copies = bookCopyRepository.findByBookId(new ObjectId(mdl.id))
        if (copies != null) {
          response.data = Some(copies.asScala.map( x => x.branch.createDto()).toList)
        }
      }
      case None => {
        response.success = false
        response.errors.+("No book found!")
      }
    }
    response
  }
}
