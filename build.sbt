name := """Springboot Scala Mongodb"""
version := "0.0.1-SNAPSHOT"

import Dependencies._
import sbt.Keys.libraryDependencies

lazy val springVersion = "2.1.4.RELEASE"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.repl",
      scalaVersion := "2.12.2",
      version      := "0.3.0"
    )),
    name := "spring-boot-scala-mongodb",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-web" % springVersion,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-actuator" % springVersion,
    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-security" % springVersion,
    libraryDependencies += "org.springframework.security.oauth" % "spring-security-oauth2" % "2.3.5.RELEASE",
    libraryDependencies += "org.springframework.security" % "spring-security-jwt" % "1.0.9.RELEASE",

    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-data-mongodb" % springVersion,
    libraryDependencies += "com.h2database" % "h2" % "1.4.195",
    
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-core" % "2.9.8",
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.9.8",
    libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.8",
    libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % "2.9.8",
    libraryDependencies += "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % "2.9.8",
    libraryDependencies += "com.fasterxml.jackson.module" % "jackson-module-scala_2.12" % "2.9.8",
    libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.3.2",
    libraryDependencies += "javax.xml.bind" % "jaxb-api" % "2.3.1",
    libraryDependencies += "org.glassfish.jaxb" % "jaxb-runtime" % "2.3.1",
    libraryDependencies += "org.scala-lang.modules" % "scala-java8-compat_2.12"  % "0.9.0",
    //libraryDependencies += "org.scala-lang.modules" % "scala-collection-compat_2.12"  % "2.1.2",

    libraryDependencies += "org.springframework.boot" % "spring-boot-starter-test" % springVersion
  )


// set the main class for the main 'run' task
// change Compile to Test to set it for 'test:run'
mainClass in (Compile, run) := Some("org.repl.poc.lmsdata.Application")
