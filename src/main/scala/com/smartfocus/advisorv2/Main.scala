package com.smartfocus.advisorv2

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import scala.util.{ Success, Failure }
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App with Config with StatusService {
  override protected implicit val system: ActorSystem = ActorSystem()
  override protected implicit val materializer: ActorMaterializer = ActorMaterializer()

  val serviceBinding = Http().bindAndHandle(routes, httpInterface, httpPort)

  serviceBinding onComplete {
    case Success(s) => log.info(s"StatusService was binded successfully")
    case Failure(t) => log.error(s"StatusService binding was unsuccesful; [${t.getMessage}]")
  }
}

