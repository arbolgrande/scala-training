package ddd.repository

import slick.jdbc.hikaricp.HikariCPJdbcDataSource
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

sealed case class HikariJdbcConfig(
  dataSource: HikariDataSource,
  config:     HikariConfig
) extends HikariCPJdbcDataSource(dataSource, config) {

  override val maxConnections: Option[Int] = None
  
}


sealed abstract class JdbcUrlBuilder(
  host:   String,
  dbName: String
) {
  
  def getJdbcUrl(): String

}

case class MysqlJdbcUrlBuilder(
  host:   String,
  dbName: String
) extends JdbcUrlBuilder(
  host   = host, 
  dbName = dbName
) {

  def getJdbcUrl(): String =
    s"jdbc:mysql://${host}/${dbName}"

}