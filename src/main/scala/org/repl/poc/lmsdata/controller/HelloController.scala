package org.repl.poc.lmsdata.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestBody, RequestMapping, RequestMethod, RestController}

@RestController
@RequestMapping(value = Array("/api"))
class HelloController @Autowired() () {

  @RequestMapping(value = Array("/echo"), method = Array(RequestMethod.POST))
  def echo(@RequestBody body: String): String = {
    body
  }
}
