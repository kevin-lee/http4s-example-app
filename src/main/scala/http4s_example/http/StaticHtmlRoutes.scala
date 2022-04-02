package http4s_example.http

import cats.syntax.all._
import cats.effect.Sync
import org.http4s.dsl.io.{->, /, GET, Root}
import org.http4s.{HttpRoutes, Response, StaticFile}

/** @author Kevin Lee
  * @since 2018-06-16
  */
object StaticHtmlRoutes {
  def apply[F[_]: Sync](notFound: => F[Response[F]]): HttpRoutes[F] =
    HttpRoutes.of[F] {

      case request @ GET -> Root / filename if filename.endsWith(".html") =>
        StaticFile
          .fromResource[F](name = s"/$filename", req = request.some)
          .getOrElseF(notFound)
    }
}
