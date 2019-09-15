/**
  * 型パラメータについていろいろまとめてみる
  */

object TypeParamSample {

  // ここが基本形
  class Hoge[T](val t: T) {
    def get(): T = t
    def isStringType(implicit ev: T =:= String) = true
  }

  def main(args: Array[String]): Unit = {
    val hoge  = new Hoge[String]("hoge")
    val hoge2 = new Hoge[Int](1)
    // compile error!
    // println(hoge2.isStringType)
    println(hoge.get())
  }

}

object TypeParamSample2 {

  class Animal
  class Mammals extends Animal
  class Human   extends Mammals

  // Humanを上限にした、Japaneseクラスを生成する。
  // これによって、Humanクラスをクラスパラメータに指定しなければcompileエラーになる。
  class Japanese[T <: Human]

  // compilation success!
  val japanese: Japanese[Human]       = new Japanese[Human]
  // compile error!
  // val fakeJapanese: Japanese[Mammals] = new Japanese[Mammals]


  class NeoJapanese[-T]
  def get(neo: NeoJapanese[Mammals]) = println("is neo")

  // compile success!
  get(new NeoJapanese[Mammals])
  // compile success!
   get(new NeoJapanese[Animal])
  // compile error!
  // get(new NeoJapanese[Human])

}