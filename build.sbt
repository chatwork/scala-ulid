import Dependencies._
import sbt._

ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(scalaVersion.value)

def crossScalacOptions(scalaVersion: String): Seq[String] =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2L, scalaMajor)) if scalaMajor >= 12 =>
      Seq.empty
    case Some((2L, scalaMajor)) if scalaMajor <= 11 =>
      Seq("-Yinline-warnings")
  }

lazy val baseSettings = Seq(
  organization := "com.chatwork",
  homepage := Some(url("https://github.com/chatwork/scala-ulid")),
  licenses := List("The MIT License" -> url("http://opensource.org/licenses/MIT")),
  developers := List(
    Developer(
      id = "j5ik2o",
      name = "Junichi Kato",
      email = "j5ik2o@gmail.com",
      url = url("https://blog.j5ik2o.me")
    ),
    Developer(
      id = "exoego",
      name = "TATSUNO Yasuhiro",
      email = "ytatsuno.jp@gmail.com",
      url = url("https://www.exoego.net")
    )
  ),
  scalaVersion := Versions.scala213Version,
  crossScalaVersions := Seq(Versions.scala211Version, Versions.scala212Version, Versions.scala213Version),
  scalacOptions ++= (
    Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-encoding",
      "UTF-8",
      "-language:_",
      "-Ydelambdafy:method",
      "-target:jvm-1.8",
      "-Yrangepos",
      "-Ywarn-unused"
    ) ++ crossScalacOptions(scalaVersion.value)
  ),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("snapshots"),
    Resolver.sonatypeRepo("releases")
  ),
  semanticdbEnabled := true,
  semanticdbVersion := scalafixSemanticdb.revision,
  Test / publishArtifact := false,
  Test / parallelExecution := false
)

val library = (project in file("library"))
  .settings(baseSettings)
  .settings(
    name := "scala-ulid",
    libraryDependencies ++= Seq(
      scalatest.scalatest  % Test,
      scalatest.scalacheck % Test
    )
  )

lazy val benchmark = (project in file("benchmark"))
  .settings(baseSettings)
  .settings(
    name := "scala-ulid-benchmark",
    skip in publish := true,
    libraryDependencies ++= Seq(
      ulid.ulidCreator,
      ulid.ulid,
      ulid.ulid4s,
      ulid.airframe
    )
  )
  .enablePlugins(JmhPlugin)
  .dependsOn(library)

val root = (project in file("."))
  .settings(baseSettings)
  .settings(
    name := "scala-ulid-root",
    publish / skip := true
  )
  .aggregate(library)

// --- Custom commands
addCommandAlias("lint", ";scalafmtCheck;test:scalafmtCheck;scalafmtSbtCheck;scalafixAll --check")
addCommandAlias("fmt", ";scalafmtAll;scalafmtSbt")
