package http4s_example

import cats.effect.{Async, ExitCode, Sync}
import fs2.Stream
import http4s_example.config.AppConfig
import http4s_example.http.{HelloWorldRoutes, StaticHtmlRoutes}
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.dsl.Http4sDsl
import org.http4s.server.Router
import org.http4s.{HttpApp, HttpRoutes}

object MainServer {
  def stream[F[*]: Async: Http4sDsl](appConfig: AppConfig): Stream[F, ExitCode] =
    BlazeServerBuilder[F]
      .bindHttp(
        appConfig.server.port.value.value,
        appConfig.server.host.value.renderString
      )
      .withHttpApp(appRoutes)
      .serve

  def helloWorldService[F[*]: Sync: Http4sDsl]: HttpRoutes[F] =
    HelloWorldRoutes[F]

  def staticHtmlService[F[*]: Sync](implicit dsl: Http4sDsl[F]): HttpRoutes[F] = {
    import dsl._
    StaticHtmlRoutes[F](dsl.NotFound())
  }

  def appRoutes[F[*]: Sync: Http4sDsl]: HttpApp[F] =
    Router(
      "/hello" -> helloWorldService,
      "/html"  -> staticHtmlService
    ).orNotFound

}
