package io.kevinlee.http4sexampleapp

import cats.effect.IO
import extras.cats.syntax.all._
import hedgehog._
import hedgehog.runner._
import org.http4s._
import org.http4s.dsl.Http4sDsl
import org.http4s.syntax.all._

object HelloWorldSpec extends Properties {

  def helloWorldService(testDesc: String): String = s"HelloWorldService $testDesc"

  override def tests: List[Test] = List(
    example(helloWorldService("""/ should return "Hello, World""""), testSlashShouldReturnHelloWorld),
    example(helloWorldService("""/ should return 200""""), testSlashShouldReturn200),
    property(helloWorldService("""/{name} should return "Hello, {name}""""), testSlashNameShouldReturnHelloName),
    property(helloWorldService("/{name} should return 200"), testSlashNameShouldReturn200),
    property(
      helloWorldService("/{add}/int1/int2 should return the result of int1 + int2"),
      testSlashAddInt1Int2ShouldReturnInt1PlusInt2
    ),
  )

  implicit val ioDsl: Http4sDsl[IO] = org.http4s.dsl.io

  def retHelloWorld(path: String): Response[IO] = (for {

    uri <- IO.delay(Uri.fromString(raw"/$path"))
             .t
             .foldF(
               IO.raiseError(_),
               IO(_)
             )
    getHW = Request[IO](Method.GET, uri)
    response <- HelloWorldService.service[IO].orNotFound(getHW)
  } yield response).unsafeRunSync()

  def testSlashShouldReturnHelloWorld: Result = {
    val expected = raw"""{"message":"Hello, World"}"""
    val actual   = retHelloWorld("").as[String].unsafeRunSync()
    actual ==== expected
  }

  def testSlashShouldReturn200: Result = {
    val expected = Status.Ok
    val actual   = retHelloWorld("").status
    actual ==== expected
  }

  def testSlashNameShouldReturnHelloName: Property = for {
    name <- Gen.string(Gen.alpha, Range.linear(1, 10)).log("name")
  } yield {
    val expected = raw"""{"message":"Hello, $name"}"""
    val actual   = retHelloWorld(name).as[String].unsafeRunSync()
    actual ==== expected
  }

  def testSlashNameShouldReturn200: Property = for {
    name <- Gen.string(Gen.alpha, Range.linear(1, 10)).log("name")
  } yield {
    val actual = retHelloWorld(name).status
    actual ==== Status.Ok
  }

  def testSlashAddInt1Int2ShouldReturnInt1PlusInt2: Property = for {
    n1 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n1")
    n2 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n2")
  } yield {
    val expected = raw"""{"result":${(n1.toLong + n2.toLong).toString}}"""
    val actual   = retHelloWorld(s"add/${n1.toString}/${n2.toString}").as[String].unsafeRunSync()
    actual ==== expected
  }

}
