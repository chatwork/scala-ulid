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

## Benchmark

```
[info] Benchmark                                                   Mode      Cnt        Score   Error  Units
[info] AirframeBenchmark.generateULID                            sample  1949073      834.596 ± 4.537  ns/op
[info] AirframeBenchmark.generateULID:generateULID·p0.00         sample               761.000          ns/op
[info] AirframeBenchmark.generateULID:generateULID·p0.50         sample               794.000          ns/op
[info] AirframeBenchmark.generateULID:generateULID·p0.90         sample               826.000          ns/op
[info] AirframeBenchmark.generateULID:generateULID·p0.95         sample               953.000          ns/op
[info] AirframeBenchmark.generateULID:generateULID·p0.99         sample              1184.000          ns/op
[info] AirframeBenchmark.generateULID:generateULID·p0.999        sample              4359.408          ns/op
[info] AirframeBenchmark.generateULID:generateULID·p0.9999       sample             26592.000          ns/op
[info] AirframeBenchmark.generateULID:generateULID·p1.00         sample           1001472.000          ns/op
[info] AirframeBenchmark.generateUUID                            sample  1544972      280.909 ± 2.711  ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p0.00         sample                77.000          ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p0.50         sample               300.000          ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p0.90         sample               311.000          ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p0.95         sample               316.000          ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p0.99         sample               330.000          ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p0.999        sample              1536.162          ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p0.9999       sample             63584.173          ns/op
[info] AirframeBenchmark.generateUUID:generateUUID·p1.00         sample            151808.000          ns/op
[info] ChatworkBenchmark.generateULID                            sample  1903117      315.481 ± 3.543  ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p0.00         sample                13.000          ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p0.50         sample               327.000          ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p0.90         sample               336.000          ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p0.95         sample               340.000          ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p0.99         sample               356.000          ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p0.999        sample              2009.528          ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p0.9999       sample             73472.000          ns/op
[info] ChatworkBenchmark.generateULID:generateULID·p1.00         sample            688128.000          ns/op
[info] ChatworkBenchmark.generateUUID                            sample  1529420      290.684 ± 3.610  ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p0.00         sample                47.000          ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p0.50         sample               302.000          ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p0.90         sample               313.000          ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p0.95         sample               318.000          ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p0.99         sample               343.000          ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p0.999        sample              3092.632          ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p0.9999       sample             73351.411          ns/op
[info] ChatworkBenchmark.generateUUID:generateUUID·p1.00         sample            644096.000          ns/op
[info] ChatworkBenchmark.incrementULID                           sample  1395792       63.288 ± 0.668  ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p0.00       sample                17.000          ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p0.50       sample                59.000          ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p0.90       sample                63.000          ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p0.95       sample                66.000          ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p0.99       sample                77.000          ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p0.999      sample               343.000          ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p0.9999     sample              9536.000          ns/op
[info] ChatworkBenchmark.incrementULID:incrementULID·p1.00       sample             65664.000          ns/op
[info] SulkyBenchmark.generateULID                               sample  1865414      465.148 ± 3.941  ns/op
[info] SulkyBenchmark.generateULID:generateULID·p0.00            sample               244.000          ns/op
[info] SulkyBenchmark.generateULID:generateULID·p0.50            sample               461.000          ns/op
[info] SulkyBenchmark.generateULID:generateULID·p0.90            sample               471.000          ns/op
[info] SulkyBenchmark.generateULID:generateULID·p0.95            sample               476.000          ns/op
[info] SulkyBenchmark.generateULID:generateULID·p0.99            sample               496.000          ns/op
[info] SulkyBenchmark.generateULID:generateULID·p0.999           sample              4880.000          ns/op
[info] SulkyBenchmark.generateULID:generateULID·p0.9999          sample             81466.688          ns/op
[info] SulkyBenchmark.generateULID:generateULID·p1.00            sample            631808.000          ns/op
[info] SulkyBenchmark.generateUUID                               sample  1579075      273.653 ± 2.438  ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p0.00            sample                32.000          ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p0.50            sample               294.000          ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p0.90            sample               306.000          ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p0.95            sample               310.000          ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p0.99            sample               327.000          ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p0.999           sample              1536.000          ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p0.9999          sample             61957.914          ns/op
[info] SulkyBenchmark.generateUUID:generateUUID·p1.00            sample            106112.000          ns/op
[info] SulkyBenchmark.incrementULID                              sample  1492476       61.466 ± 0.668  ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p0.00          sample                13.000          ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p0.50          sample                57.000          ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p0.90          sample                62.000          ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p0.95          sample                63.000          ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p0.99          sample                74.000          ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p0.999         sample               301.523          ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p0.9999        sample              9120.000          ns/op
[info] SulkyBenchmark.incrementULID:incrementULID·p1.00          sample            101248.000          ns/op
[info] ULIDCreatorBenchmark.generateUUID                         sample  1557765      282.790 ± 3.348  ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p0.00      sample                45.000          ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p0.50      sample               298.000          ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p0.90      sample               309.000          ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p0.95      sample               313.000          ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p0.99      sample               332.000          ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p0.999     sample              1810.468          ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p0.9999    sample             65536.000          ns/op
[info] ULIDCreatorBenchmark.generateUUID:generateUUID·p1.00      sample            678912.000          ns/op
[info] ULIDCreatorBenchmark.incrementULID                        sample  1981375       52.879 ± 0.391  ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p0.00    sample                 4.000          ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p0.50    sample                51.000          ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p0.90    sample                54.000          ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p0.95    sample                56.000          ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p0.99    sample                61.000          ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p0.999   sample               142.000          ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p0.9999  sample              3213.248          ns/op
[info] ULIDCreatorBenchmark.incrementULID:incrementULID·p1.00    sample             50944.000          ns/op
```
