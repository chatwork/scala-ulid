package jmh

import java.util.UUID
import java.util.concurrent.TimeUnit

import de.huxhorn.sulky.ulid.ULID
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit, Scope, State}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class SulkyBenchmark {

  val ulid  = new ULID()
  var value = ulid.nextValue()

  @Benchmark
  def generateUUID(): Unit = {
    UUID.randomUUID()
  }

  @Benchmark
  def generateULID(): Unit = {
    ulid.nextValue()
  }

  @Benchmark
  def incrementULID(): Unit = {
    value = ulid.nextMonotonicValue(value)
  }

}
