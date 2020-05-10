package ddd.repository

import slick.jdbc._

case class Sample(id: Option[Long], name: String)

object SampleTable extends SampleTable

trait SampleTable extends MySQLTable[Sample] {

  import driver.api._

  val baseConfigMap = Map(
    "master" -> BaseConfig("test", "master"),
    "slave"  -> BaseConfig("test", "slave")
  )

  class Query extends SlickQuery(new Table (_)) {}
  lazy val query = new Query

  class Table(tag: Tag) extends SlickTable(tag, "test") {

    def id   = column[Long]  ("id",  O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")

    def * = (id.?, name) <> (Sample.tupled, Sample.unapply)
  }
}

trait MySQLTable[M] {

  val driver = MySQLProfile

  val baseConfigMap: Map[String, BaseConfig]

  val query: SlickQuery

  type Table      <: driver.Table[M]
  type SlickTable =  driver.Table[M]

  type Query      <: slick.lifted.TableQuery[Table]
  type SlickQuery =  slick.lifted.TableQuery[Table]

}