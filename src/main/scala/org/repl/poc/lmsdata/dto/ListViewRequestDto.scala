package org.repl.poc.lmsdata.dto

import scala.collection.mutable

class ListViewRequestDto {
  var filters: mutable.Map[String, Any] = mutable.Map()
  var pageSize: Int = 0
  var pageNum: Int = 0
  var sortMap: mutable.Map[String, String] = mutable.Map()
}