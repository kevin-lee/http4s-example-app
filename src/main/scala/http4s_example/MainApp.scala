package http4s_example

import cats.effect.{IO, IOApp}
import cats.syntax.all._
import extras.cats.syntax.all._
import http4s_example.config.AppConfig
import http4s_example.config.AppConfig.InvalidConfigError
import org.http4s.dsl.Http4sDsl

object MainApp extends IOApp.Simple {
  implicit val dsl: Http4sDsl[IO] = org.http4s.dsl.io

  override def run: IO[Unit] =
    for {
      appConfig <- AppConfig
                     .load[IO]
                     .eitherT
                     .foldF(
                       err =>
                         IO.raiseError[AppConfig](
                           InvalidConfigError(err.prettyPrint(0))
                         ),
                       _.pure[IO]
                     )
      _         <- MainServer
                     .stream[IO](appConfig)
                     .compile
                     .drain
    } yield ()

}
