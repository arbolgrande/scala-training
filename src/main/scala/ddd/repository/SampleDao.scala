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
      value <- db.run(action(table.query))
    } yield value
  }
}

object Sample2 {
  import com.typesafe.config._
  val conf = ConfigFactory.load()
  println(conf.getString("mysql.test.host_master"))
  println(conf.getString("mysql.test.databaseName"))
}