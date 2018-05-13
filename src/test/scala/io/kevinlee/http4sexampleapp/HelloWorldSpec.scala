package io.kevinlee.http4sexampleapp

import cats.effect.IO
import org.http4s._
import org.http4s.implicits._
import org.scalacheck.{Arbitrary, Gen, Shrink}
import org.specs2.ScalaCheck

@SuppressWarnings(Array(
  "org.wartremover.warts.NonUnitStatements", "org.wartremover.warts.Throw"
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
    def retHelloWorld(name: String): Response[IO] = {
      val uri = Uri.fromString(raw"/$name") match {
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
      retHelloWorld("").as[String].unsafeRunSync() must beEqualTo(raw"""{"message":"Hello, World"}""")
    }
    "/ should return 200" >> {
      retHelloWorld("").status must beEqualTo(Status.Ok)
    }

    """/{name} should return "Hello, {name}"""" >> {
      prop { name: String =>
        retHelloWorld(name).as[String].unsafeRunSync() must beEqualTo(raw"""{"message":"Hello, $name"}""")
      }.setShrink(reducedCombinationShrink)
    }
    "/{name} should return 200" >> {
      prop { name: String =>
        retHelloWorld(name).status must beEqualTo(Status.Ok)
      }.setShrink(reducedCombinationShrink)
    }

  }

}
