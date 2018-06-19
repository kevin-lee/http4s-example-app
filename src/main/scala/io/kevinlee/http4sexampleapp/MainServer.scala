package io.kevinlee.http4sexampleapp

import cats.effect.IO
import fs2.StreamApp.ExitCode
import fs2.{StreamApp, Stream => Fs2Stream}
import org.http4s.HttpService
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object MainServer extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]): Fs2Stream[IO, ExitCode] = ServerStream.stream
}

object ServerStream {

  def helloWorldService: HttpService[IO] = HelloWorldService.service
  def staticHtmlService: HttpService[IO] = StaticHtmlService.service

  def stream(implicit ec: ExecutionContext): Fs2Stream[IO, ExitCode] =
    BlazeBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .mountService(helloWorldService, "/hello")
      .mountService(staticHtmlService, "/html")
      .serve
}
