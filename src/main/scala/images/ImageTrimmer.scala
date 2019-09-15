/**
  * Save how to trim images in scala.
  */

package images

import java.net.URL
import javax.imageio._
import java.io._

import scala.util.{Failure, Success, Try}

/**
  * scalaで画像のトリミングをしたかったのでまとめる。といってもjavaの資産をそのまま使うので
  * javaっぽい感じにはなる。何かいい方法があれば追記
  */

/**
  * 画像トリミング用のクラス。
  * タプルにするとわかりにくくなりそうなので定義してるだけ
  * @param startX
  * @param startY
  * @param width
  * @param height
  */
case class ImageParams(
  startX: Int,
  startY: Int,
  width:  Int,
  height: Int
)

object ImageTrimmer {

  /**
    * @param args
    * このメソッドはシンプルに画像を取得する処理
    * URLのバリデーションは省略
    */
  def main2(args: Array[String]): Unit = {
    val url        = args.head
    val openStream = Try(new URL(url).openStream())
    openStream match {
      case Success(stream) =>
        val buffer = Stream.continually(stream.read).takeWhile(_ != -1).map(_.byteValue).toArray
        val file   = new BufferedOutputStream(new FileOutputStream("hoge.jpeg"))
        file.write(buffer)
        file.close()
      case Failure(e) => println(e)
    }
  }

  /**
    * @param args
    * トリミングを含めた画像取得の処理
    */
  def main(args: Array[String]): Unit = {
    val url = args.head
    def trimImage(bytes: Array[Byte], params: ImageParams): Array[Byte] = {
      val image        = ImageIO.read(new ByteArrayInputStream(bytes))
      val trimmedImage = image.getSubimage(params.startX, params.startY, params.width, params.height)
      val bos          = new ByteArrayOutputStream()
      val os           = new BufferedOutputStream(bos)
      ImageIO.write(trimmedImage, "jpeg", os)
      bos.toByteArray
    }
    val openStream = Try(new URL(url).openStream())
    openStream match {
      case Success(stream) =>
        val buffer       = Stream.continually(stream.read).takeWhile(_ != -1).map(_.byteValue).toArray
        // 指定した画像のサイズの大きさを変えるサイズを指定すると、Exceptionが発生するので注意すること。
        // BufferedImageクラスに画像のサイズを取得するメソッドがあるので、それを使ってあげるとなお良い
        val trimmedBytes = trimImage(buffer, ImageParams(0, 0, 100, 100))
        val file         = new BufferedOutputStream(new FileOutputStream("hoge.jpeg"))
        file.write(trimmedBytes)
        file.close()
      case Failure(e) => println(e)
    }
  }
}