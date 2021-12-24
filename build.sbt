ThisBuild / organization := "io.kevinlee"
ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version      := "0.0.1-SNAPSHOT"

lazy val http4sExampleApp = (project in file("."))
  .settings(
    name := "http4s-example-app",
    wartremoverErrors ++= Warts.allBut(Wart.ImplicitParameter, Wart.Any, Wart.Nothing),
    libraryDependencies ++= Seq(
      "org.http4s"    %% "http4s-blaze-server" % props.Http4sVersion,
      "org.http4s"    %% "http4s-circe"        % props.Http4sVersion,
      "org.http4s"    %% "http4s-dsl"          % props.Http4sVersion,
      "ch.qos.logback" % "logback-classic"     % props.LogbackVersion,
    ),
    libraryDependencies ++= Seq(
      "io.kevinlee" %% "extras-cats"                  % props.extrasVersion,
      "io.kevinlee" %% "extras-hedgehog-cats-effect3" % props.extrasVersion,
      "qa.hedgehog" %% "hedgehog-core"                % props.hedgehogVersion,
      "qa.hedgehog" %% "hedgehog-runner"              % props.hedgehogVersion,
      "qa.hedgehog" %% "hedgehog-sbt"                 % props.hedgehogVersion
    ).map(_ % Test),
    testFrameworks ~=
      (frameworks => (TestFramework("hedgehog.sbt.Framework") +: frameworks).distinct),
  )

lazy val props = new {
  val Http4sVersion   = "0.23.7"
  val LogbackVersion  = "1.2.10"
  val extrasVersion   = "0.4.0"
  val hedgehogVersion = "0.8.0"
}
