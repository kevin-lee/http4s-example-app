package io.kevinlee.http4sexampleapp

import cats.effect._
import io.circe.Json
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl

object HelloWorldService {

  def service[F[_]: Sync](implicit dsl: Http4sDsl[F]): HttpRoutes[F] = {
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root =>
        Ok(Json.obj("message" -> Json.fromString("Hello, World")))

      case GET -> Root / name =>
        Ok(Json.obj("message" -> Json.fromString(s"Hello, $name")))

      case GET -> Root / "add" / IntVar(a) / IntVar(b) =>
        Ok(Json.obj("result" -> Json.fromLong(a.toLong + b.toLong)))
    }
  }
}
