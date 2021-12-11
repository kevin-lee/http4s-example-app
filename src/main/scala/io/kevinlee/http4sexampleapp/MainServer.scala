package io.kevinlee.http4sexampleapp

import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.io._
import org.http4s.server.Router
import org.http4s.{HttpApp, HttpRoutes}

object MainServer extends IOApp {
  val dsl: Http4sDsl[IO] = org.http4s.dsl.io

  def helloWorldService: HttpRoutes[IO] = HelloWorldService.service[IO](implicitly, dsl)
  def staticHtmlService: HttpRoutes[IO] = StaticHtmlService.service[IO](dsl.NotFound())

  def appRoutes: HttpApp[IO] = Router("/hello" -> helloWorldService, "/html" -> staticHtmlService).orNotFound

  override def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(appRoutes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)

}
