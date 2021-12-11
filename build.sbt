import Version._

lazy val root = (project in file("."))
  .settings(
    organization := "io.kevinlee",
    name         := "http4s-example-app",
    version      := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.7",
    wartremoverErrors ++= Warts.allBut(Wart.ImplicitParameter, Wart.Any, Wart.Nothing),
    libraryDependencies ++= Seq(
      "org.http4s"     %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"     %% "http4s-circe"        % Http4sVersion,
      "org.http4s"     %% "http4s-dsl"          % Http4sVersion,
      "ch.qos.logback"  % "logback-classic"     % LogbackVersion,
      "org.specs2"     %% "specs2-core"         % Specs2Version     % Test,
      "org.specs2"     %% "specs2-scalacheck"   % Specs2Version     % Test,
      "org.scalacheck" %% "scalacheck"          % ScalaCheckVersion % Test,
    ),
    libraryDependencies ++= Seq(
      "io.kevinlee" %% "extras-cats"     % props.extrasVersion,
      "qa.hedgehog" %% "hedgehog-core"   % props.hedgehogVersion,
      "qa.hedgehog" %% "hedgehog-runner" % props.hedgehogVersion,
      "qa.hedgehog" %% "hedgehog-sbt"    % props.hedgehogVersion
    ).map(_ % Test)
  )

lazy val props = new {
  val extrasVersion   = "0.2.0"
  val hedgehogVersion = "0.8.0"
}
