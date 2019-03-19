package org.repl.poc.lmsdata.dto

import scala.collection.mutable

class ServiceResponse[T] {
  var success: Boolean = false
  val errors: mutable.MutableList[ServiceResponseError] = mutable.MutableList()
  var data: Option[T] = _
}
