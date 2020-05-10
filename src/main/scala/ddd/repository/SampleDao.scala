package ddd.repository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import slick.jdbc.{MySQLProfile, JdbcProfile}
import slick.dbio.DBIOAction
import slick.dbio.NoStream

case class SampleRepo() extends SampleTable {

  import driver.api._
  // dsをcloseする処理を追加する
  def get() = 
  MySQLDBAction(SampleTable){ slick =>
    slick.filter(_.name === "hote").result
  }
  
}

object MySQLDBAction extends MySQLConnector {

  def apply[A, T <: MySQLTable[_]]
  (table: T, host: String = "master")
  (action: T#SlickQuery => DBIOAction[A, NoStream, Nothing]): Future[A] = {
    for {
      db    <- getDatabase(table.baseConfigMap(host))
      value <- invoke[T, A](table, host, {
        slick => db.run(action(slick))
      })
    } yield value
  }
  def invoke[T <: MySQLTable[_], A](table: T, host: String, block: T#SlickQuery => Future[A]): Future[A] = {
    for {
      db <- getDatabase(table.baseConfigMap(host))
      value <- block(table.query)
    } yield value
  }
}

object Sample2 {
  import com.typesafe.config._
  def hoge(s: String)(str: String => Future[String]): Future[String] = {
    str(s)
  }
  hoge("d"){ str => 
    print(str)
    Future(str)
  }
  def main(args: Array[String]): Unit = {
     hoge("d"){ str => 
    print(str)
    Future(str)
  }
    val conf = ConfigFactory.load()
    println(conf.getString("mysql.test.host_master"))
    println(conf.getString("mysql.test.databaseName"))
  }
}