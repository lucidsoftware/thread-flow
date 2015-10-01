lazy val threadFlowCore = Project("thread-flow-core", file("thread-flow-core"))
  .settings(
    autoScalaLibrary := false,
    crossPaths := false
  )

lazy val threadFlowJava = Project("thread-flow-java", file("thread-flow-java"))
  .dependsOn(threadFlowCore, threadFlowTest2_11 % Test)
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
    libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.4" % Test
  )

lazy val threadFlowSlf4j = Project("thread-flow-slf4j", file("thread-flow-sl4j"))
  .dependsOn(threadFlowCore)
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
    libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.12"
  )

lazy val threadFlowApacheHttpClient = Project("thread-flow-httpclient", file("thread-flow-httpclient"))
  .configs(IntegrationTest)
  .dependsOn(threadFlowCore, threadFlowTest2_11 % IntegrationTest)
  .settings(Defaults.itSettings)
  .settings(
    autoScalaLibrary := false,
    crossPaths := false,
    libraryDependencies ++= Seq(
      "org.apache.httpcomponents" % "httpclient" % "4.5",
      "org.specs2" %% "specs2-core" % "3.6.4" % IntegrationTest
    )
  )

lazy val threadFlowScala = Project("thread-flow-scala", file("thread-flow-scala"))
  .dependsOn(threadFlowCore, threadFlowJava)
  .settings(defaultScalariformSettings)
  .settings(
    libraryDependencies += "org.specs2" %% "specs2-core" % "3.6.4" % Test
  )
  .cross

lazy val threadFlowScala2_10 = threadFlowScala("2.10.6").dependsOn(threadFlowTest2_10 % Test)

lazy val threadFlowScala2_11 = threadFlowScala("2.11.7").dependsOn(threadFlowTest2_11 % Test)

lazy val threadFlowAkka = Project("thread-flow-akka", file("thread-flow-akka"))
  .settings(defaultScalariformSettings)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.3.14"
    )
  )
  .cross
  .dependsOn(threadFlowScala)

lazy val threadFlowAkka2_10 = threadFlowAkka("2.10.5")

lazy val threadFlowAkka2_11 = threadFlowAkka("2.11.7")

lazy val threadFlowAkkaAggregate = threadFlowAkka.aggregate(threadFlowAkka2_10, threadFlowAkka2_11)

lazy val threadFlowPlay = Project("thread-flow-play", file("thread-flow-play"))
  .settings(defaultScalariformSettings)
  .settings(
    libraryDependencies += "com.typesafe.play" %% "play" % "2.3.8"
  )
  .cross
  .dependsOn(threadFlowAkka, threadFlowScala)

lazy val threadFlowPlay2_10 = threadFlowPlay("2.10.6")

lazy val threadFlowPlay2_11 = threadFlowPlay("2.11.7")

lazy val threadFlowPlayAggregate = threadFlowAkka.aggregate(threadFlowPlay2_10, threadFlowPlay2_11)

lazy val threadFlowTest = Project("thread-flow-test", file("thread-flow-test"))
  .dependsOn(threadFlowCore)
  .settings(defaultScalariformSettings)
  .cross

lazy val threadFlowTest2_10 = threadFlowTest("2.10.6")

lazy val threadFlowTest2_11 = threadFlowTest("2.11.7")

lazy val threadFlowTestAggregate = threadFlowAkka.aggregate(threadFlowTest2_10, threadFlowTest2_11)

inThisBuild(Seq(
  credentials += Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    System.getenv("SONATYPE_USERNAME"),
    System.getenv("SONATYPE_PASSWORD")
  ),
  javacOptions ++= Seq(
    "-source", "1.7",
    "-target", "1.7"
  ),
  organization := "com.lucidchart",
  pomExtra := {
    <url>https://github.com/lucidsoftware/relate</url>
      <licenses>
        <license>
          <name>Apache License</name>
          <url>http://www.apache.org/licenses/</url>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:lucidsoftware/thread-flow.git</url>
        <connection>scm:git:git@github.com:lucidsoftware/thread-flow.git</connection>
      </scm>
      <developers>
        <developer>
          <id>lucidsoftware</id>
          <name>Lucid Software</name>
        </developer>
      </developers>
  },
  pgpPassphrase := Some(Array()),
  pgpPublicRing := file(System.getProperty("user.home")) / ".pgp" / "pubring",
  pgpSecretRing := file(System.getProperty("user.home")) / ".pgp" / "secring",
  pomIncludeRepository := { _ => false },
  publishMavenStyle := true,
  publishTo := Some(
    if (version.value.endsWith("SNAPSHOT")) {
      Resolver.sonatypeRepo("snapshots")
    } else {
      "releases" at s"https://oss.sonatype.org/service/local/staging/deploy/maven2"
    }
  ),
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature"
  ),
  version := "0.0-SNAPSHOT"
))
