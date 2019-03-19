package org.repl.poc.lmsdata.dto

import java.time.LocalDateTime

abstract class Item {
  var id: String = _
  var createdDate: LocalDateTime = _
  var createdByUID: String = _
  var modifiedDate: LocalDateTime = _
  var modifiedByUID: String = _
}
