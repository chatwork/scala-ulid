package com.chatwork.scala.ulid

import org.scalacheck.{Gen, Shrink}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

import scala.util.Random

class ULIDSpec extends AnyFreeSpec with Matchers with ScalaCheckDrivenPropertyChecks {
  implicit val noShrink: Shrink[String] = Shrink.shrinkAny
  val ulidGen: Gen[ULID]                = Gen.const(ULID.generate())

  "ULID" - {
    "generate" in forAll(ulidGen) { ulid =>
      val str = ulid.asString
      println(str)
      ULID.parseULID(str).get shouldBe ulid
    }
    "too long ulid string" in {
      val str = (0 until 27)
        .map(_ => ULID.ENCODING_CHARS(Random.nextInt(ULID.ENCODING_CHARS.length)))
        .mkString
      ULID.parseULID(str).isFailure shouldBe true
    }
    "timestamp overflow ulid string" in {
      val timestamp = ULID.internalWriteCrockford(ULID.TIMESTAMP_OVERFLOW_MASK, 10)
      val str       = Seq(timestamp, "ZYZJZ9QZ", "ZZ5JP0VK").mkString
      ULID.parseULID(str).isFailure shouldBe true
    }
    "generateMonotonic" in forAll(ulidGen) { ulid =>
      val ulid2 = ULID.generateMonotonic(ulid)
      val ulid3 = ULID.generateMonotonic(ulid2)
      val ulid4 = ULID.generateMonotonic(ulid2, () => ulid2.toEpochMilliAsLong)

      ulid should be < (ulid2)
      ulid2 should be < (ulid3)
      ulid3 should be > (ulid2)
      ulid should be < (ulid3)
      ulid.compareTo(ulid) shouldBe 0
      ulid2 should be < ulid4
    }
    "generateStrictlyMonotonic" in forAll(ulidGen) { ulid =>
      val ulid2 = ULID.generateStrictlyMonotonic(ulid).get
      ulid should be < (ulid2)
    }
    "toBytes" in forAll(ulidGen) { ulid =>
      val bytes = ulid.toBytes
      ULID.fromBytes(bytes).get shouldBe ulid
    }
    "too long ulid bytes" in forAll(ulidGen) { ulid =>
      val bytes = ulid.toBytes :+ 'a'.toByte
      ULID.fromBytes(bytes).isFailure shouldBe true
    }

  }
}
