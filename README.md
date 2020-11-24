# scala-ulid

scala-ulid is a library for ULID.

## Installation

Add the following to your sbt build (2.11.x, 2.12.x, 2.13.x):

```scala
resolvers += "Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/repositories/releases/"

val version = "..."

libraryDependencies += Seq(
  "com.chatwork" %% "scala-ulid" % version
)
```

## Usage

```scala
val ulid = ULID.generate()
val str = ulid.asString
println(str) // 01EQWGKT1S68Y9YM5TV34RQVQA
ULID.parseULID(str).foreach{ uild =>
  println(ulid) // 01EQWGKT1S68Y9YM5TV34RQVQA
}

val bytes = uild.toBytes
ULID.fromBytes(bytes).foreach { ulid =>
  println(ulid) // 01EQWGKT1S68Y9YM5TV34RQVQA
}
```
