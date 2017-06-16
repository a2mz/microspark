// Your profile name of the sonatype account. The default is the same with the organization value
sonatypeProfileName := "(com.github.a2mz)"

// To sync with Maven central, you need to supply the following information:
publishMavenStyle := true

// License of your choice
licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/a2mz/microspark"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/a2mz/microspark"),
    "scm:git@github.com:a2mz/microspark.git"
  )
)
developers := List(
  Developer(id="om", name="Morozov Oleksandr", email="mz.oleksandr@gmail.com", url=url("https://github.com/a2mz"))
)