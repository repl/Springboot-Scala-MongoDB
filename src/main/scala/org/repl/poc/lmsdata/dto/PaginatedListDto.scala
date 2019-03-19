package org.repl.poc.lmsdata.dto

import scala.collection.mutable

class PaginatedListDto[T] {
  var results: mutable.Buffer[T] = mutable.Buffer()
  var pageSize: Int = 0
  var pageNum: Int = 0
  var totalCount: Long = 0
}
