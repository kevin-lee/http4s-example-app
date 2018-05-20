package io.kevinlee.http4sexampleapp

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import org.scalacheck.{Arbitrary, Gen, Shrink}
import org.specs2.ScalaCheck

@SuppressWarnings(Array(
  "org.wartremover.warts.NonUnitStatements",
  "org.wartremover.warts.Throw",
  "org.wartremover.warts.Any"
))
class HelloWorldSpec extends org.specs2.mutable.Specification with ScalaCheck {

  private val reducedCombinationShrink = Shrink[String] { s =>
    (for {
      n <- s.length to 1 by -1
      x <- s.combinations(n)
      if x !== s
    } yield x).toStream
  }

  "HelloWorldService" >> {
    def retHelloWorld(path: String): Response[IO] = {
      val uri = Uri.fromString(raw"/$path") match {
        case Right(url) =>
          url
        case Left(failure) =>
          throw failure
      }
      val getHW = Request[IO](Method.GET, uri)
      new HelloWorldService[IO].service.orNotFound(getHW).unsafeRunSync()
    }

    implicit def alphaNumString: Arbitrary[String] =
      Arbitrary(Gen.alphaNumStr.filter(_.length > 2))

    """/ should return "Hello, World"""" >> {
      val expected = raw"""{"message":"Hello, World"}"""
      val actual = retHelloWorld("").as[String].unsafeRunSync()
      actual must be equalTo expected
    }
    "/ should return 200" >> {
      val expected = Status.Ok
      val actual = retHelloWorld("").status
      actual must be equalTo expected
    }

    """/{name} should return "Hello, {name}"""" >> {
      prop { name: String =>
        val expected = raw"""{"message":"Hello, $name"}"""
        val actual = retHelloWorld(name).as[String].unsafeRunSync()
        actual must be equalTo expected
      }.setShrink(reducedCombinationShrink)
    }
    "/{name} should return 200" >> {
      prop { name: String =>
        val actual = retHelloWorld(name).status
        actual must be equalTo Status.Ok
      }.setShrink(reducedCombinationShrink)
    }

    "/{add}/int1/int2 should return the result of int1 + int2" >> {
      prop { (n1: Int, n2: Int) =>
        val expected = raw"""{"result":${n1.toLong + n2.toLong}}"""
        val actual = retHelloWorld(s"add/$n1/$n2").as[String].unsafeRunSync()
        actual must be equalTo expected
      }
    }

  }

}
