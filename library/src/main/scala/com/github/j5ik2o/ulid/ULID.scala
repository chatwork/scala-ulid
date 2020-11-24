package com.github.j5ik2o.ulid

import java.security.{NoSuchAlgorithmException, SecureRandom}
import java.time.Instant
import java.util.concurrent.TimeUnit

import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.util.{Random, Try}

@SerialVersionUID(3563159514112487717L)
final case class ULID(mostSignificantBits: Long, leastSignificantBits: Long)
    extends Ordered[ULID]
    with Serializable {
  import ULID._

  def increment: ULID = {
    val lsb = leastSignificantBits
    if (lsb != 0xffffffffffffffffL)
      new ULID(mostSignificantBits, lsb + 1)
    else {
      val msb = mostSignificantBits
      if ((msb & RANDOM_MSB_MASK) != RANDOM_MSB_MASK)
        new ULID(msb + 1, 0)
      else
        new ULID(msb & TIMESTAMP_MSB_MASK, 0)
    }
  }

  def toBytes: Array[Byte] = {
    val result = new Array[Byte](16)
    for (i <- 0 until 8) {
      result(i) = ((mostSignificantBits >> ((7 - i) * 8)) & 0xff).toByte
    }
    for (i <- 8 until 16) {
      result(i) = ((leastSignificantBits >> ((15 - i) * 8)) & 0xff).toByte
    }
    result
  }

  def toEpochMilliAsLong: Long = mostSignificantBits >>> 16

  def toEpochMilli: FiniteDuration = Duration(toEpochMilliAsLong, TimeUnit.MILLISECONDS)

  def toInstant: Instant = Instant.ofEpochMilli(toEpochMilliAsLong)

  override def compare(that: ULID): Int = {
    if (mostSignificantBits < that.mostSignificantBits) -1
    else if (mostSignificantBits > that.mostSignificantBits) 1
    else if (leastSignificantBits < that.leastSignificantBits) -1
    else if (leastSignificantBits > that.leastSignificantBits) 1
    else 0
  }

  def asString: String = {
    Seq(
      internalWriteCrockford(toEpochMilliAsLong, 10),
      internalWriteCrockford(
        (mostSignificantBits & 0xffffL) << 24 | leastSignificantBits >>> 40,
        8
      ),
      internalWriteCrockford(leastSignificantBits, 8)
    ).mkString
  }

}

object ULID {
  val defaultRandomGen: SecureRandom =
    try { SecureRandom.getInstance("NativePRNGNonBlocking") }
    catch {
      case _: NoSuchAlgorithmException =>
        SecureRandom.getInstanceStrong
    }

  def generate(
      timestamp: Long = System.currentTimeMillis(),
      randomGen: Random = defaultRandomGen
  ): ULID = {
    checkTimestamp(timestamp)
    val (random1, random2)   = genRandom(randomGen)
    val mostSignificantBits  = (timestamp << 16) | (random1 >>> 24)
    val leastSignificantBits = (random1 << 40) | random2
    new ULID(mostSignificantBits, leastSignificantBits)
  }

  def generateMonotonic(
      previousID: ULID,
      timestamp: Long = System.currentTimeMillis(),
      randomGen: Random = defaultRandomGen
  ): ULID = {
    if (previousID.toEpochMilliAsLong == timestamp)
      previousID.increment
    else
      generate(timestamp, randomGen)
  }

  def generateStrictlyMonotonic(
      previousID: ULID,
      timestamp: Long = System.currentTimeMillis(),
      randomGran: Random = defaultRandomGen
  ): Option[ULID] = {
    val result = generateMonotonic(previousID, timestamp, randomGran)
    if (result.compareTo(previousID) < 1)
      None
    else
      Some(result)
  }

  @inline
  private def genRandom(randomGen: Random) = {
    val bytes = new Array[Byte](10)
    randomGen.nextBytes(bytes)

    var random1 = 0L
    var random2 = 0L

    random1 = (bytes(0x0) & 0xff).toLong << 32
    random1 |= (bytes(0x1) & 0xff).toLong << 24
    random1 |= (bytes(0x2) & 0xff).toLong << 16
    random1 |= (bytes(0x3) & 0xff).toLong << 8
    random1 |= (bytes(0x4) & 0xff).toLong

    random2 = (bytes(0x5) & 0xff).toLong << 32
    random2 |= (bytes(0x6) & 0xff).toLong << 24
    random2 |= (bytes(0x7) & 0xff).toLong << 16
    random2 |= (bytes(0x8) & 0xff).toLong << 8
    random2 |= (bytes(0x9) & 0xff).toLong
    (random1, random2)
  }

  private val ULID_STRING_LENGTH = 26
  private val ULID_BYTES_LENGTH  = 16
  private val TIMESTAMP_MSB_MASK = 0xffffffffffff0000L
  private val RANDOM_MSB_MASK    = 0xffffL
  private val MASK_BITS          = 5
  private val MASK               = 0x1f
  private[ulid] val ENCODING_CHARS =
    Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
      'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z')
  private val DECODING_CHARS = Array( // 0
    -1, -1, -1, -1, -1, -1, -1, -1, // 8
    -1, -1, -1, -1, -1, -1, -1, -1, // 16
    -1, -1, -1, -1, -1, -1, -1, -1, // 24
    -1, -1, -1, -1, -1, -1, -1, -1, // 32
    -1, -1, -1, -1, -1, -1, -1, -1, // 40
    -1, -1, -1, -1, -1, -1, -1, -1, // 48
    0, 1, 2, 3, 4, 5, 6, 7,         // 56
    8, 9, -1, -1, -1, -1, -1, -1,   // 64
    -1, 10, 11, 12, 13, 14, 15, 16, // 72
    17, 1, 18, 19, 1, 20, 21, 0,    // 80
    22, 23, 24, 25, 26, -1, 27, 28, // 88
    29, 30, 31, -1, -1, -1, -1, -1, // 96
    -1, 10, 11, 12, 13, 14, 15, 16, // 104
    17, 1, 18, 19, 1, 20, 21, 0,    // 112
    22, 23, 24, 25, 26, -1, 27, 28, // 120
    29, 30, 31)

  private[ulid] val TIMESTAMP_OVERFLOW_MASK = 0xffff000000000000L
  private def checkTimestamp(timestamp: Long): Unit = {
    if ((timestamp & TIMESTAMP_OVERFLOW_MASK) != 0)
      throw new IllegalArgumentException(
        "ULID does not support timestamps after +10889-08-02T05:31:50.655Z!"
      )
  }

  private[ulid] def internalWriteCrockford(
      value: Long,
      count: Int
  ): String = {
    (0 until count).map { i =>
      val index = ((value >>> ((count - i - 1) * MASK_BITS)) & MASK).asInstanceOf[Int]
      ENCODING_CHARS(index)
    }.mkString
  }

  private[ulid] def internalParseCrockford(input: String): Long = {
    val length = input.length
    if (length > 12) {
      throw new IllegalArgumentException("input length must not exceed 12 but was " + length + "!")
    }
    input.zipWithIndex.foldLeft(0L) {
      case (result, (current, i)) =>
        val value =
          if (current < DECODING_CHARS.length)
            DECODING_CHARS(current)
          else
            -1
        if (value < 0)
          throw new IllegalArgumentException("Illegal character '" + current + "'!")
        result | value.toLong << ((length - 1 - i) * MASK_BITS)
    }
  }

  def isValid(ulidString: String): Boolean = {
    parseULID(ulidString).isSuccess
  }

  def parseULID(ulidString: String): Try[ULID] =
    Try {
      if (ulidString.length != ULID_STRING_LENGTH)
        throw new IllegalArgumentException("ulidString must be exactly 26 chars long.")
      val timeString = ulidString.substring(0, 10)
      val timestamp  = internalParseCrockford(timeString)
      if ((timestamp & TIMESTAMP_OVERFLOW_MASK) != 0)
        throw new IllegalArgumentException(
          "ulidString must not exceed '7ZZZZZZZZZZZZZZZZZZZZZZZZZ'!"
        )
      val part1String = ulidString.substring(10, 18)
      val part1       = internalParseCrockford(part1String)
      val part2String = ulidString.substring(18)
      val part2       = internalParseCrockford(part2String)

      val mostSignificantBits  = (timestamp << 16) | (part1 >>> 24)
      val leastSignificantBits = part2 | (part1 << 40)
      new ULID(mostSignificantBits, leastSignificantBits)
    }

  def fromBytes(data: Array[Byte]): Try[ULID] =
    Try {
      if (data.length != ULID_BYTES_LENGTH)
        throw new IllegalArgumentException("data must be 16 bytes in length!")
      val mostSignificantBits = (0 until 8).foldLeft(0L) {
        case (result, i) =>
          (result << 8) | (data(i) & 0xff)
      }
      val leastSignificantBits = (8 until 16).foldLeft(0L) {
        case (result, i) =>
          (result << 8) | (data(i) & 0xff)
      }
      new ULID(mostSignificantBits, leastSignificantBits)
    }
}
