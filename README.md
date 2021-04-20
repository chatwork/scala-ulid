# scala-ulid

scala-ulid is a library for ULID.

## Installation

Add the following to your sbt build (2.11.x, 2.12.x, 2.13.x):

```scala
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

## Benchmark

https://github.com/chatwork/scala-ulid/blob/main/benchmark/src/main/scala/jmh/ULIDBenchmark.scala

<table>
<tr>
  <th width="10%">-</th><th width="30%">airframe/airframe-control</th><th width="30%">sulky/sulky-ulid</th><th width="30%">chatwork/scala-ulid</th>
</tr>
<tr>
  <th>Random number generation</th><td>Calling Random#nextDouble 16 times</td><td>Calling Random#nextLong twice (upper 48 bits useless random number generation)</td><td>Calling Random#nextBytes(10) only once</td>
</tr>
<tr>
  <th>Latency 95%tile(nsec)</th><td>1038<br/>(newULIDString=1024nsec)</td><td>524</td><td>460</td>
</tr>
<tr>
   <th>Latency Max(msec)</th><td>1.329<br/>(newULIDString=0.755msec)</td><td>0.721</td><td>0.790</td>
</tr>
</table>

- `java.util.UUID#randomUUID` is 95%tile = 327nsec, max = 0.703msec

