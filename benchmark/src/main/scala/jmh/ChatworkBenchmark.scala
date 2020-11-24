package jmh

import java.util.UUID
import java.util.concurrent.TimeUnit

import com.chatwork.scala.ulid.ULID
import org.openjdk.jmh.annotations._

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ChatworkBenchmark {

  var ulid = ULID.generate()

  @Benchmark
  def generateUUID(): Unit = {
    UUID.randomUUID()
  }

  @Benchmark
  def generateULID(): Unit = {
    ULID.generate()
  }

  @Benchmark
  def incrementULID(): Unit = {
    ulid = ULID.generateMonotonic(ulid)
  }

}
