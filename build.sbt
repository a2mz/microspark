import sbt.Keys.{scalacOptions, _}

val buildSettings = Defaults.coreDefaultSettings ++ Seq(
  name := "microspark",
  version := "1.1",
  scalacOptions ++= Seq(),
  scalaVersion := "2.11.10",
  crossScalaVersions := Seq("2.12.1","2.11.10"),
  publishMavenStyle := true,
  libraryDependencies ++= {
    val akkaVersion = "10.0.7"
    List(
      "de.heikoseeberger" %% "akka-http-play-json" % "1.15.0",
      "com.typesafe.akka" %% "akka-http" % akkaVersion
    )
  },
   scalacOptions ++= Seq("-Xfatal-warnings","-feature","-language:higherKinds", "-language:implicitConversions" , "-language:postfixOps", "-deprecation")
)



lazy val root = project
  .in(file("."))
  .settings(buildSettings)
