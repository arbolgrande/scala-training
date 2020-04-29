/**
  * Seleniumを使ったテストツールを作成したい
 */

package test

import org.scalatest._
import org.scalatest.selenium.Chrome

class SeleniumTestSpec extends FlatSpec with Matchers with Chrome {
 "www.scalatest.org" should "have the correct title" in {
   go to "http://www.scalatest.org/"
   pageTitle should be ("ScalaTest")
 }
 "Assertions Page" should "be found in ScalaTestDoc" in {
   go to "http://www.scalatest.org/"
   click on linkText("Scaladoc")
   click on linkText("Scaladoc for ScalaTest 3.0.0")
   click on "index-input"
   enter("Assertions")
   click on xpath(".//*[@id='tpl']/ol[1]/ol/li[1]/a[2]/span")

   switch to frame("template")
//    find(tagName("h1")).get.text should be ("Assertions")

   setCaptureDir(".")  // スクリーンショットの保存先としてカレントディレクトリを指定
   capture to "Assertions Page.png"
   quit()
 }
}