import sbt._

object Dependencies {

  object Versions {
    val scala211Version = "2.11.12"
    val scala212Version = "2.12.10"
    val scala213Version = "2.13.5"

    val scalaTestVersion = "3.2.8"

  }

  import Versions._

  object scalatest {
    val scalatest  = "org.scalatest"     %% "scalatest"       % scalaTestVersion
    val scalacheck = "org.scalatestplus" %% "scalacheck-1-14" % "3.2.2.0"
  }

  object ulid {
    val ulidCreator = "com.github.f4b6a3"   % "ulid-creator"          % "2.3.3"
    val ulid        = "de.huxhorn.sulky"    % "de.huxhorn.sulky.ulid" % "8.2.0"
    val ulid4s      = "net.petitviolet"    %% "ulid4s"                % "0.4.1"
    val airframe    = "org.wvlet.airframe" %% "airframe-ulid"         % "21.4.1"
  }

}
