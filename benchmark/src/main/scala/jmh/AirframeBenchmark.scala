package jmh

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit, Scope, State}
import wvlet.airframe.control.ULID

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class AirframeBenchmark {
  @Benchmark
  def generate(): Unit = {
    ULID.newULID
  }

}
