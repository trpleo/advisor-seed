package com.smartfocus.advisorv2

import spray.json.DefaultJsonProtocol

case class Status(uptime: String, memoryStatus: MemoryStatus, javaArgs: String, appName: String, appVersion: String, at: String)

case class MemoryStatus(totalMemory: String, freeMemory: String, usedMemory: String)

trait Protocol extends DefaultJsonProtocol {
  implicit val memoryFormat = jsonFormat3(MemoryStatus.apply)
  implicit val statusFormat = jsonFormat(Status, "uptime", "memoryInfo", "javaArgs", "applicationName", "applicationVersion", "lastCompiledAt")
}
