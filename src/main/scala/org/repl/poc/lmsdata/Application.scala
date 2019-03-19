package org.repl.poc.lmsdata

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}

@ComponentScan
@Configuration
@EnableAutoConfiguration
class SampleConfig {
  @Bean
  def jacksonScalaModule(): Module = {
    DefaultScalaModule
  }
}

object Application extends App {
  SpringApplication.run(classOf[BootConfig]);
}
