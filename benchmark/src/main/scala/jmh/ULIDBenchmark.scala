package jmh

import java.util.UUID
import java.util.concurrent.TimeUnit

import com.chatwork.scala.ulid.{ULID => cwULID}
import de.huxhorn.sulky.ulid.{ULID => sULID}
import net.petitviolet.ulid4s.{ULID => ULID4S}
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit, Scope, State}
import wvlet.airframe.ulid.{ULID => afULID}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ULIDBenchmark {

  @Benchmark
  def randomUUID_toString(): Unit = {
    UUID.randomUUID().toString
  }

  @Benchmark
  def ulid4s_ULID_newULID_toString() = {
    ULID4S.generate
  }

  @Benchmark
  def airframe_ULID_newULIDString(): Unit = {
    afULID.newULIDString
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
