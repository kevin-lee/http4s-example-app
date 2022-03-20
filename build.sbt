ThisBuild / organization := "io.kevinlee"
ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version      := "0.1.0-SNAPSHOT"

lazy val http4sExampleApp = (project in file("."))
  .settings(
    name := "http4s-example-app",
    wartremoverErrors ++= Warts.allBut(Wart.ImplicitParameter, Wart.Any, Wart.Nothing),
    libraryDependencies ++= libs.http4s ++ List(libs.logback) ++ libs.testLibs,
    testFrameworks ~=
      (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
  )

lazy val props = new {
  val Http4sVersion   = "0.23.10"
  val LogbackVersion  = "1.2.11"
  val extrasVersion   = "0.4.0"
  val hedgehogVersion = "0.8.0"
}

lazy val libs = new {
  lazy val http4s = List(
    "org.http4s" %% "http4s-blaze-server" % props.Http4sVersion,
    "org.http4s" %% "http4s-circe"        % props.Http4sVersion,
    "org.http4s" %% "http4s-dsl"          % props.Http4sVersion,
  )

  lazy val logback = "ch.qos.logback" % "logback-classic" % props.LogbackVersion

  lazy val testLibs = List(
    "io.kevinlee" %% "extras-cats"                  % props.extrasVersion,
    "io.kevinlee" %% "extras-hedgehog-cats-effect3" % props.extrasVersion,
    "qa.hedgehog" %% "hedgehog-core"                % props.hedgehogVersion,
    "qa.hedgehog" %% "hedgehog-runner"              % props.hedgehogVersion,
    "qa.hedgehog" %% "hedgehog-sbt"                 % props.hedgehogVersion,
  ).map(_ % Test)
}
