package io.kevinlee.http4sexampleapp

/**
  * @author Kevin Lee
  * @since 2018-05-19
  */
object TestPredef {
  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  implicit final class EqualityOps[A](self: A) {
    def ===(other: A): Boolean = self == other
    def !==(other: A): Boolean = self != other
  }
}
