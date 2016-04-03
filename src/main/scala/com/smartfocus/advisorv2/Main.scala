package com.smartfocus.advisorv2

import akka.actor.ActorSystem
import akka.event.{ Logging, LoggingAdapter }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{ Failure, Success }

object Main extends App with Config {
  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val log: LoggingAdapter = Logging(system, "main")

  val statusService = StatusService()
  val commandService = CommandService()

  val serviceName = s"${statusService.serviceNamePrefix}/boff"

  val routes = statusService.routes ~ commandService.routes

  val serviceBinding = Http().bindAndHandle(routes, httpInterface, httpPort)

  serviceBinding onComplete {
    case Success(s) => log.info(s"HTTP server was started successful. All bindings is done.")
    case Failure(t) => log.error(s"HTTP server initialization is failed; [${t.getMessage}]")
  }

  system.awaitTermination()
}

