package http4s_example.config

import cats.effect._
import eu.timepit.refined.pureconfig._
import eu.timepit.refined.types.net.PortNumber
import http4s_example.data.error.AppError
import io.estatico.newtype.macros.newtype
import org.http4s.Uri
import pureconfig.generic.semiauto._
import pureconfig.module.http4s._
import pureconfig.{ConfigReader, ConfigSource}

/** @author Kevin Lee
  * @since 2022-04-02
  */
final case class AppConfig(
  server: AppConfig.ServerConfig
)
object AppConfig {

  implicit val appConfigReader: ConfigReader[AppConfig] = deriveReader

  def load[F[*]: Sync]: F[ConfigReader.Result[AppConfig]] =
    Sync[F].delay(ConfigSource.default.load[AppConfig])

  final case class ServerConfig(host: HostAddress, port: Port)
  object ServerConfig {
    implicit val serverConfigConfigReader: ConfigReader[ServerConfig] = deriveReader
  }

  @newtype case class HostAddress(value: Uri)
  object HostAddress {
    implicit val hostAddressConfigReader: ConfigReader[HostAddress] = deriving

  }
  @newtype case class Port(value: PortNumber)
  object Port {
    implicit val portConfigReader: ConfigReader[Port] = deriving
  }

  final case class InvalidConfigError(override val message: String) extends AppError(message)
}
