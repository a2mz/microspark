import sbt.Keys._

val buildSettings = Defaults.coreDefaultSettings ++ Seq(
  organization:="com.github.a2mz",
  name := "microspark",
// version := "1.4",
  scalacOptions ++= Seq(),
  scalaVersion in ThisBuild := "2.12.2",
  crossScalaVersions in ThisBuild := Seq("2.12.2", "2.11.10"),
  publishMavenStyle := true,
  libraryDependencies ++= {
    val akkaVersion = "10.0.7"
    List(
      "de.heikoseeberger" %% "akka-http-play-json" % "1.15.0",
      "com.typesafe.akka" %% "akka-http"           % akkaVersion
    )
  },
  scalacOptions ++= Seq(
    "-Xfatal-warnings",
    "-feature",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-deprecation")
)




lazy val root = project
  .in(file("."))
  .settings(buildSettings)



