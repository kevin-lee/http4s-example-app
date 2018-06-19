package io.kevinlee.http4sexampleapp

import cats.effect._

import org.http4s.dsl.io._
import org.http4s.{HttpService, StaticFile}

/**
  * @author Kevin Lee
  * @since 2018-06-16
  */
object StaticHtmlService {
  val service: HttpService[IO] = HttpService[IO] {

    case request @ GET -> Root / filename if filename.endsWith(".html") =>
      StaticFile.fromResource(name = s"/$filename", req = Some(request))
                .getOrElseF(NotFound())
  }
}
