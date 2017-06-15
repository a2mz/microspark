import sbt.Keys.{scalacOptions, _}

val buildSettings = Defaults.coreDefaultSettings ++ Seq(
  name := "microspark",
  version := "1.0-SNAPSHOT",
  scalacOptions ++= Seq(),
  scalaVersion := "2.12.1",
  crossScalaVersions := Seq("2.12.1"),
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

lazy val publishSettings = Seq(
  pomExtra := (
    <url>https://github.com/a2mz/microspark</url>
      <licenses>
        <license>
          <name>Apache 2.0</name>
          <url>http://www.apache.org/licenses/</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:a2mz/microspark.git</url>
        <connection>scm:git@github.com:a2mz/microspark.git</connection>
      </scm>
      <developers>
        <developer>
          <id>a2mz</id>
          <name>Morozov Oleksandr</name>
          <url>github.com/a2mz</url>
        </developer>
      </developers>
  ),
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (version.value.toLowerCase.endsWith("snapshot"))
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("staging" at nexus + "service/local/staging/deploy/maven2")
  }
)
scalaVersion in ThisBuild := "2.12.1"

lazy val root = project
  .in(file("."))
  .settings(buildSettings ++ publishSettings)
