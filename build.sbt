import Version._

lazy val root = (project in file("."))
  .settings(
    organization := "io.kevinlee",
    name := "http4s-example-app",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.15",
    scalacOptions ++= Seq(
      "-deprecation",             // Emit warning and location for usages of deprecated APIs.
      "-feature",                 // Emit warning and location for usages of features that should be imported explicitly.
      "-language:higherKinds",
      "-unchecked",               // Enable additional warnings where generated code depends on assumptions.
      "-Xfatal-warnings",         // Fail the compilation if there are any warnings.
      "-Xlint",                 // Enable recommended additional warnings.
      "-Ywarn-adapted-args",      // Warn if an argument list is modified to match the receiver.
      "-Ywarn-dead-code",         // Warn when dead code is identified.
      "-Ywarn-inaccessible",      // Warn about inaccessible types in method signatures.
      "-Ywarn-nullary-override",  // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
      "-Ywarn-numeric-widen"      // Warn when numerics are widened.
    ),
    wartremoverErrors ++= Warts.allBut(Wart.ImplicitParameter, Wart.Nothing),

    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion,
      "org.specs2"      %% "specs2-core"         % Specs2Version % Test,
      "org.specs2"      %% "specs2-scalacheck"   % Specs2Version % Test,
      "org.scalacheck"  %% "scalacheck"          % ScalaCheckVersion % Test,
    )
  )
