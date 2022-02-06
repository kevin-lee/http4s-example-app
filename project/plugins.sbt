addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")

val sbtDevOopsVersion = "2.15.0"

addSbtPlugin("io.kevinlee" %% "sbt-devoops-scala"     % sbtDevOopsVersion)
addSbtPlugin("io.kevinlee" %% "sbt-devoops-sbt-extra" % sbtDevOopsVersion)

addSbtPlugin("org.wartremover" % "sbt-wartremover" % "2.4.16")
