package jmh

import java.util.concurrent.TimeUnit

import com.chatwork.scala.ulid.ULID
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ChatworkBenchmark {

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
