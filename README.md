# scala-ulid

[![CI](https://github.com/chatwork/scala-ulid/workflows/CI/badge.svg)](https://github.com/chatwork/scala-ulid/actions?query=workflow%3ACI)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.chatwork/scala-ulid_2.13/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.chatwork/scala-ulid_2.13)
[![Scaladoc](http://javadoc-badge.appspot.com/com.chatwork/scala-ulid_2.13.svg?label=scaladoc)](http://javadoc-badge.appspot.com/com.chatwork/scala-ulid_2.13/com/chatwork/scala/ulid/index.html?javadocio=true)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

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

```
sbt:scala-ulid-root> benchmark/jmh:run -i 5 -wi 5 -f1 -t1

[info] Benchmark                                                                              Mode      Cnt        Score    Error  Units
[info] ULIDBenchmark.airframe_ULID_newULIDString                                            sample  1093757      227.598 ±  4.123  ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p0.00          sample               160.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p0.50          sample               205.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p0.90          sample               224.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p0.95          sample               236.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p0.99          sample               286.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p0.999         sample              3787.872           ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p0.9999        sample             25696.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULIDString:airframe_ULID_newULIDString·p1.00          sample            536576.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString                                         sample  1118731      213.279 ±  2.168  ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p0.00    sample               132.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p0.50    sample               200.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p0.90    sample               217.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p0.95    sample               225.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p0.99    sample               266.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p0.999   sample              1808.000           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p0.9999  sample             14630.317           ns/op
[info] ULIDBenchmark.airframe_ULID_newULID_toString:airframe_ULID_newULID_toString·p1.00    sample            564224.000           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString                                           sample  1824959      437.502 ±  3.722  ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p0.00        sample               273.000           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p0.50        sample               308.000           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p0.90        sample               578.000           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p0.95        sample               589.000           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p0.99        sample               649.000           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p0.999       sample              2124.160           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p0.9999      sample             86400.000           ns/op
[info] ULIDBenchmark.cwULID_generateULID_asString:cwULID_generateULID_asString·p1.00        sample            569344.000           ns/op
[info] ULIDBenchmark.randomUUID_toString                                                    sample  1404265      446.363 ±  6.941  ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p0.00                          sample               117.000           ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p0.50                          sample               451.000           ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p0.90                          sample               502.000           ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p0.95                          sample               509.000           ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p0.99                          sample               541.000           ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p0.999                         sample              2956.000           ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p0.9999                        sample            101449.395           ns/op
[info] ULIDBenchmark.randomUUID_toString:randomUUID_toString·p1.00                          sample           1443840.000           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString                                            sample  1250885      664.955 ±  6.563  ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p0.00          sample               369.000           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p0.50          sample               673.000           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p0.90          sample               720.000           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p0.95          sample               730.000           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p0.99          sample               773.000           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p0.999         sample              3628.456           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p0.9999        sample             89076.659           ns/op
[info] ULIDBenchmark.sUlid_generateULID_toString:sUlid_generateULID_toString·p1.00          sample           1452032.000           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString                                           sample  1169642     5325.069 ± 26.806  ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p0.00        sample              4416.000           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p0.50        sample              5152.000           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p0.90        sample              5584.000           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p0.95        sample              5632.000           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p0.99        sample              6968.000           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p0.999       sample             74541.696           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p0.9999      sample            143625.139           ns/op
[info] ULIDBenchmark.ulid4s_ULID_newULID_toString:ulid4s_ULID_newULID_toString·p1.00     
```

