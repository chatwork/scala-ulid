package jmh

import java.util.concurrent.TimeUnit

import com.github.f4b6a3.ulid.UlidCreator
import org.openjdk.jmh.annotations.{Benchmark, BenchmarkMode, Mode, OutputTimeUnit, Scope, State}

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.SampleTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ULIDCreatorBenchmark {

  @Benchmark
  def increment(): Unit = {
    UlidCreator.getUlid
  }

}
