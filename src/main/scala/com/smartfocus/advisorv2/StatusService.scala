package com.smartfocus.advisorv2

import java.lang.management.ManagementFactory
import akka.http.scaladsl.server.Directives._
import scala.concurrent.duration._
import scala.collection.JavaConversions._
import scala.util.Try

trait StatusService extends BaseService {
  protected val serviceName = "advisorv2/StatusService"
  protected val routes = pathPrefix("status") {
    get {

      log.debug("/status request")

      val runtimeMxBean = ManagementFactory.getRuntimeMXBean
      val status = Duration(runtimeMxBean.getUptime, MILLISECONDS).toString()
      val javaArgs = runtimeMxBean.getInputArguments().toList.mkString(";")
      val mb = 1024 * 1024
      val totalMemory = Runtime.getRuntime.totalMemory() / mb
      val freeMemory = Runtime.getRuntime.freeMemory() / mb
      val usedMemory = totalMemory - freeMemory

      val pckg = Try(getClass.getPackage)
      val name = pckg map (_.getName) getOrElse ("N/A")
      val version = pckg map (_.getImplementationVersion) getOrElse ("N/A") match {
        case null => "na"
        case s    => s
      }
      val ver = com.smartfocus.advisorv2.BuildInfo.version
      val buildnumber = com.smartfocus.advisorv2.BuildInfo.buildInfoBuildNumber
      val at = com.smartfocus.advisorv2.BuildInfo.builtAtString

      val statusObj = Status(status, MemoryStatus(s"$totalMemory MB", s"$freeMemory MB", s"$usedMemory MB"), javaArgs, name, s"$ver::$buildnumber", at)

      log.info(s"/status request: [$statusObj]")

      complete(statusObj)
    }
  }

  implicit class TryWithOption[T](tryThis: Try[T]) {
    def getAsOption = tryThis match {
      case scala.util.Success(v)  => Some(v)
      case scala.util.Failure(ex) => None // TODO: logging
    }
  }
}

