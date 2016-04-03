package com.smartfocus.advisorv2

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

trait BaseService extends Protocol with SprayJsonSupport with Config {
  val serviceNamePrefix = "advisor-v2"

  val serviceName: String
  val routes: Route

  val system: ActorSystem
  val materializer: ActorMaterializer
  val log: LoggingAdapter = Logging(system, serviceName)
}
