package jmh

import java.util.UUID
import java.util.concurrent.TimeUnit

import com.chatwork.scala.ulid.{ULID => cwULID}
import de.huxhorn.sulky.ulid.{ULID => sULID}
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit, Scope, State}
import wvlet.airframe.control.{ULID => afULID}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ULIDBenchmark {

  @Benchmark
  def randomUUID_toString(): Unit = {
    UUID.randomUUID().toString
  }

  @Benchmark
  def airframe_ULID_newULID_toString(): Unit = {
    afULID.newULID.toString()
  }

  @Benchmark
  def cwULID_generateULID_asString(): Unit = {
    cwULID.generate().asString
  }

  val sUlid = new sULID()

  @Benchmark
  def sUlid_generateULID_toString(): Unit = {
    sUlid.nextValue().toString
  }

}
