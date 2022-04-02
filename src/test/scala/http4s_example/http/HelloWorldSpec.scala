package http4s_example.http

import cats.effect.IO
import extras.cats.syntax.all._
import extras.hedgehog.cats.effect.CatsEffectRunner
import hedgehog.runner.{Properties, Test, example, property}
import hedgehog.{Gen, Property, Range, Result}
import org.http4s.dsl.Http4sDsl
import org.http4s.{Method, Request, Response, Status, Uri}

/** @author Kevin Lee
  * @since 2022-04-02
  */
object HelloWorldSpec extends Properties {

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

  private def helloWorldService(testDesc: String): String = s"HelloWorldService $testDesc"

  implicit val ioDsl: Http4sDsl[IO] = org.http4s.dsl.io

  def retHelloWorld(path: String): IO[Response[IO]] = for {

    uri <- IO.delay(Uri.fromString(raw"/$path"))
             .eitherT
             .foldF(
               IO.raiseError(_),
               IO(_)
             )
    getHW = Request[IO](Method.GET, uri)
    response <- HelloWorldRoutes[IO].orNotFound(getHW)
  } yield response

  def testSlashShouldReturnHelloWorld: Result = {
    val expected = raw"""{"message":"Hello, World"}"""
    val actual   = retHelloWorld("").flatMap(_.as[String])

    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()
    actual.completeAs(expected)
  }

  def testSlashShouldReturn200: Result = {
    val expected = Status.Ok
    val actual   = retHelloWorld("").map(_.status)

    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()
    actual.completeAs(expected)
  }

  def testSlashNameShouldReturnHelloName: Property = for {
    name <- Gen.string(Gen.alpha, Range.linear(1, 10)).log("name")
  } yield {
    val expected = raw"""{"message":"Hello, $name"}"""
    val actual   = retHelloWorld(name).flatMap(_.as[String])

    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()
    actual.completeAs(expected)
  }

  def testSlashNameShouldReturn200: Property = for {
    name <- Gen.string(Gen.alpha, Range.linear(1, 10)).log("name")
  } yield {
    val expected = Status.Ok
    val actual   = retHelloWorld(name).map(_.status)

    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()
    actual.completeAs(expected)
  }

  def testSlashAddInt1Int2ShouldReturnInt1PlusInt2: Property = for {
    n1 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n1")
    n2 <- Gen.int(Range.linear(Int.MinValue, Int.MaxValue)).log("n2")
  } yield {
    val expected = raw"""{"result":${(n1.toLong + n2.toLong).toString}}"""
    val actual   = retHelloWorld(s"add/${n1.toString}/${n2.toString}").flatMap(_.as[String])

    import CatsEffectRunner._
    implicit val ticker: Ticker = Ticker.withNewTestContext()
    actual.completeAs(expected)

  }

}
