package org.repl.poc.lmsdata.dto

case class UserBookRatingSummaryDto(isbn: String) {
  var count: Long = _
  var highestRatings: List[UserBookRatingDto] = _
  var lowestRatings: List[UserBookRatingDto] = _
}
