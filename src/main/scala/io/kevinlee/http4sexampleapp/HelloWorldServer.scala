package io.kevinlee.http4sexampleapp

import cats.effect.{Effect, IO}
import fs2.StreamApp.ExitCode
import fs2.{StreamApp, Stream => Fs2Stream}
import org.http4s.HttpService
import org.http4s.server.blaze.BlazeBuilder

import scala.concurrent.ExecutionContext

object HelloWorldServer extends StreamApp[IO] {
  import scala.concurrent.ExecutionContext.Implicits.global

  def stream(args: List[String], requestShutdown: IO[Unit]): Fs2Stream[IO, ExitCode] = ServerStream.stream[IO]
}

object ServerStream {

  def helloWorldService[F[_]: Effect]: HttpService[F] = new HelloWorldService[F].service

  def stream[F[_]: Effect](implicit ec: ExecutionContext): Fs2Stream[F, ExitCode] =
    BlazeBuilder[F]
      .bindHttp(8080, "0.0.0.0")
      .mountService(helloWorldService, "/hello")
      .serve
}
