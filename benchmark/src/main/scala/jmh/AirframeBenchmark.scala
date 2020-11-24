package jmh

import java.util.UUID
import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit, Scope, State}
import wvlet.airframe.control.ULID

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class AirframeBenchmark {

  @Benchmark
  def generateUUID(): Unit = {
    UUID.randomUUID()
  }

  @Benchmark
  def generateULID(): Unit = {
    ULID.newULID
  }

}
