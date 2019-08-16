package org.repl.poc.lmsdata.mongodb.repository

import org.repl.poc.lmsdata.mongodb.model.LibraryBranchMdl
import org.springframework.data.repository.Repository

trait LibraryBranchRepository extends Repository[LibraryBranchMdl, String] {
  def save(model: LibraryBranchMdl): LibraryBranchMdl
  def findById(id: String): Option[LibraryBranchMdl]
  def findByName(name: String): Option[LibraryBranchMdl]
}
