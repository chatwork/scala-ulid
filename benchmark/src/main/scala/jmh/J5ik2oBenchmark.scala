package jmh

import java.util.concurrent.TimeUnit

import com.github.j5ik2o.ulid.ULID
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class J5ik2oBenchmark {
  var ulid = ULID.generate()

  @Benchmark
  def generate(): Unit = {
    ULID.generate()
  }

  @Benchmark
  def increment(): Unit = {
    ulid = ULID.generateMonotonic(ulid)
  }

}
