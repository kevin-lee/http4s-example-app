package http4s_example.data

import scala.util.control.NoStackTrace

/** @author Kevin Lee
  * @since 2022-04-02
  */
object error {
  abstract class AppError(val message: String) extends NoStackTrace {
    override def getMessage: String = message
  }
}
