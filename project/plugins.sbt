addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

val sbtDevOopsVersion = "2.22.0"

addSbtPlugin("io.kevinlee" %% "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" %% "sbt-devoops-sbt-extra" % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" %% "sbt-devoops-starter" % sbtDevOopsVersion)

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "3.0.5")
addSbtPlugin("ch.epfl.scala"   % "sbt-scalafix"    % "0.10.0")
addSbtPlugin("org.scalameta"   % "sbt-scalafmt"    % "2.4.6")
