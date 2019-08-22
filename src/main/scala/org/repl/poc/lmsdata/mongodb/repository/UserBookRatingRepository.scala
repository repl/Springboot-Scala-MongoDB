package org.repl.poc.lmsdata.mongodb.repository

import org.repl.poc.lmsdata.mongodb.model.{UserBookRatingMdl, UserMdl}
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.{MongoRepository, Query}

trait UserBookRatingRepository extends MongoRepository[UserBookRatingMdl, String] {
  @Query(value= "{'isbn' : ?0, rating: {$gt: 7} }", sort = "{ rating : -1 }")
  def getHighRatings(isbn: String, pageable: Pageable): java.util.List[UserBookRatingMdl]
}
