package org.repl.poc.lmsdata.mongodb.repository

import org.repl.poc.lmsdata.mongodb.model.{UserMdl}
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.Repository

trait UserRepository extends MongoRepository[UserMdl, String] {
  def findByUsername(userId: String): UserMdl
}
