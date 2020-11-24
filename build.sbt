import sbt.Keys.libraryDependencies

val scala211Version = "2.11.12"
val scala212Version = "2.12.10"
val scala213Version = "2.13.4"

lazy val baseSettings = Seq(
  organization := "com.chatwork",
  scalaVersion := scala213Version,
  crossScalaVersions := Seq(scala211Version, scala212Version, scala213Version),
  scalacOptions ++= (
    Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-encoding",
      "UTF-8",
      "-language:_",
      "-Ydelambdafy:method",
      "-target:jvm-1.8"
    ) ++ crossScalacOptions(scalaVersion.value)
  ),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases")
  ),
  parallelExecution in Test := false,
  scalafmtOnCompile in ThisBuild := true,
  envVars := Map(
    "AWS_REGION" -> "ap-northeast-1"
  )
)

def crossScalacOptions(scalaVersion: String): Seq[String] =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2L, scalaMajor)) if scalaMajor >= 12 =>
      Seq.empty
    case Some((2L, scalaMajor)) if scalaMajor <= 11 =>
      Seq("-Yinline-warnings")
  }

lazy val deploySettings = Seq(
  sonatypeProfileName := "com.chatwork",
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  pomExtra := {
    <url>https://github.com/j5ik2o/akka-persistence-dynamodb</url>
      <licenses>
        <license>
          <name>Apache 2</name>
          <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:j5ik2o/akka-persistence-dynamodb.git</url>
        <connection>scm:git:github.com/j5ik2o/akka-persistence-dynamodb</connection>
        <developerConnection>scm:git:git@github.com:j5ik2o/akka-persistence-dynamodb.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>j5ik2o</id>
          <name>Junichi Kato</name>
        </developer>
      </developers>
  },
  publishTo := sonatypePublishToBundle.value,
  credentials := {
    val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
    val gpgCredentials = (baseDirectory in LocalRootProject).value / ".gpgCredentials"
    Credentials(ivyCredentials) :: Credentials(gpgCredentials) :: Nil
  }
)

val library = (project in file("library"))
  .settings(baseSettings)
  .settings(deploySettings)
  .settings(
    name := "ulid",
    libraryDependencies ++= Seq(
      "org.scalatest"     %% "scalatest"       % "3.2.2"   % Test,
      "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0" % Test
    )
  )

lazy val benchmark = (project in file("benchmark"))
  .settings(baseSettings)
  .settings(deploySettings)
  .settings(
    name := "akka-persistence-dynamodb-benchmark",
    skip in publish := true,
    libraryDependencies ++= Seq(
      "com.github.f4b6a3"   % "ulid-creator"          % "2.3.3",
      "de.huxhorn.sulky"    % "de.huxhorn.sulky.ulid" % "8.2.0",
      "org.wvlet.airframe" %% "airframe-control"      % "20.11.0" // Library for retryable execution
    )
  )
  .enablePlugins(JmhPlugin)
  .dependsOn(library)

val root = (project in file("."))
  .settings(baseSettings)
  .settings(
    name := "ulid-root",
    skip in publish := true
  )
  .aggregate(library)
