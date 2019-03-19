package org.repl.poc.lmsdata.exception

import scala.collection.mutable

case class ServiceException(private val message: String = "",
                            private val cause: Throwable = None.orNull) extends Exception(message, cause) {
  private var businessErrorMessages: mutable.MutableList[String] = mutable.MutableList()

  def getBusinessErrorMessages: mutable.MutableList[String] = businessErrorMessages
}
