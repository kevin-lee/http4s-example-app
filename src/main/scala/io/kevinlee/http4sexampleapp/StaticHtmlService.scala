package io.kevinlee.http4sexampleapp

import cats.effect._
import cats.syntax.all._
import org.http4s.dsl.io._
import org.http4s.{HttpRoutes, Response, StaticFile}

/** @author Kevin Lee
  * @since 2018-06-16
  */
object StaticHtmlService {
  def service[F[_]: Sync](notFound: => F[Response[F]]): HttpRoutes[F] =
    HttpRoutes.of[F] {

      case request @ GET -> Root / filename if filename.endsWith(".html") =>
        StaticFile
          .fromResource[F](name = s"/$filename", req = request.some)
          .getOrElseF(notFound)
    }
}
