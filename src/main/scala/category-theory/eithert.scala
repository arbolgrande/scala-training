/**
  * How to Use EitherT
  */

import scala.concurrent.{Await, Future}
import scala.util._
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

import cats.implicits._
import cats.data.EitherT

object EitherTTraining {
  def main(args: Array[String]): Unit = {

    val either1: Either[Int, String] = Right("1")
    val either2: Either[Int, String] = Right("2")
    val either3: Either[Int, String] = Left(1)

    val eitherT1 = EitherT(Future.successful(either1))
    val eitherT2 = EitherT(Future.successful(either2))
    val eitherT3 = EitherT(Future.successful(either3))

    val result = for {
      res1 <- eitherT1
      res2 <- eitherT2
      res3 <- eitherT3
    } yield (res1, res2, res3)
    val res = result semiflatMap { case (res1, res2, res3) =>
      Future.successful(res1 + res2 + res3)
    }

    val value = res.value
    Await.ready(value, Duration.Inf)
    println(value.value)
    result.value.onComplete {
      case Success(v) => println(v)
      case Failure(ex) => println(ex)
    }
  }
}