package io.kevinlee.http4sexampleapp

import cats.effect.{Blocker, ExitCode, IO, IOApp, Resource}
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.io._
import org.http4s.server.{Router, Server}

object MainServer extends IOApp {
  val dsl: Http4sDsl[IO] = org.http4s.dsl.io

  def helloWorldService: HttpRoutes[IO]                   = HelloWorldService.service[IO](implicitly, dsl)
  def staticHtmlService(blocker: Blocker): HttpRoutes[IO] = StaticHtmlService.service[IO](dsl.NotFound())(blocker)

  override def run(args: List[String]): IO[ExitCode] =
    app
      .use(_ => IO.never)
      .as(ExitCode.Success)

  val app: Resource[IO, Server] = for {
    blocker <- Blocker[IO]
    router = Router("/hello" -> helloWorldService, "/html" -> staticHtmlService(blocker)).orNotFound
    server <- BlazeServerBuilder[IO](executionContext)
                .bindHttp(8080, "0.0.0.0")
                .withHttpApp(router)
                .resource
  } yield server
}
