package com.smartfocus.advisorv2

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

/**
 * Created by ipapp on 01/04/16.
 */
case class CommandService(implicit val system: ActorSystem, implicit val materializer: ActorMaterializer) extends BaseService {
  val serviceName = s"$serviceNamePrefix/StatusService"
  val routes = pathPrefix("cmd") {
    get {
      log.info("cmd-get")
      complete("{}")
    } ~
      post {
        log.info("cmd-post")
        complete("{}")
      }
  }
}
