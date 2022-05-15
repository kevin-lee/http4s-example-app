ThisBuild / organization := "io.kevinlee"
ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val http4sExampleApp = (project in file("."))
  .settings(
    name              := "http4s-example-app",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    wartremoverErrors ++= Warts.allBut(Wart.ImplicitParameter, Wart.ImplicitConversion, Wart.Any, Wart.Nothing),
    libraryDependencies ++= libs.all,
    testFrameworks ~=
      (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
  )

lazy val props = new {
  val NewtypeVersion    = "0.4.4"
  val RefinedVersion    = "0.9.28"
  val Http4sVersion     = "0.23.11"
  val PureconfigVersion = "0.17.1"
  val LogbackVersion    = "1.2.11"
  val extrasVersion     = "0.14.0"
  val hedgehogVersion   = "0.8.0"
}

lazy val libs = new {

  lazy val newtype = "io.estatico" %% "newtype" % props.NewtypeVersion

  lazy val refined           = "eu.timepit" %% "refined-scalacheck" % props.RefinedVersion
  lazy val refinedPureconfig = "eu.timepit" %% "refined-pureconfig" % props.RefinedVersion

  lazy val http4s = List(
    "org.http4s" %% "http4s-blaze-server" % props.Http4sVersion,
    "org.http4s" %% "http4s-circe"        % props.Http4sVersion,
    "org.http4s" %% "http4s-dsl"          % props.Http4sVersion,
  )

  lazy val pureconfigCirce      = "com.github.pureconfig" %% "pureconfig-circe"       % props.PureconfigVersion
  lazy val pureconfigGeneric    = "com.github.pureconfig" %% "pureconfig-generic"     % props.PureconfigVersion
  lazy val pureconfigCatsEffect = "com.github.pureconfig" %% "pureconfig-cats-effect" % props.PureconfigVersion
  lazy val pureconfigHttp4s     = "com.github.pureconfig" %% "pureconfig-http4s"      % props.PureconfigVersion

  lazy val logback = "ch.qos.logback" % "logback-classic" % props.LogbackVersion

  lazy val extrasCats = "io.kevinlee" %% "extras-cats" % props.extrasVersion

  lazy val testLibs = List(
    "io.kevinlee" %% "extras-hedgehog-cats-effect3" % props.extrasVersion,
    "qa.hedgehog" %% "hedgehog-core"                % props.hedgehogVersion,
    "qa.hedgehog" %% "hedgehog-runner"              % props.hedgehogVersion,
    "qa.hedgehog" %% "hedgehog-sbt"                 % props.hedgehogVersion,
  ).map(_ % Test)

  lazy val all =
    List(
      newtype,
      refined,
      refinedPureconfig,
      pureconfigCirce,
      pureconfigGeneric,
      pureconfigCatsEffect,
      pureconfigHttp4s,
      extrasCats,
      logback
    ) ++ http4s ++ testLibs
}
