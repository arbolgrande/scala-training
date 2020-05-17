package ddd.repository

import scala.util.Try
import slick.jdbc.MySQLProfile
import com.typesafe.config._
import scala.concurrent.Future

import com.zaxxer.hikari.{HikariConfig, HikariDataSource}
import java.util.concurrent.ConcurrentHashMap

import scala.concurrent.ExecutionContext.Implicits.global

trait MySQLConnector {

  import slick.jdbc.MySQLProfile.api._
  
  def getDatabase(config: BaseConfig): Future[Database] = {
    for {
      ds <- createDataSource(config)
    } yield {
      createConnection(ds)
    }
  }

  private def createDataSource(config: BaseConfig): Future[HikariJdbcConfig] = {
    Future.fromTry {
      for {
        config  <- Try(MySQLJdbcConfigLoader(config))
        jdbcUrl <- Try(MysqlJdbcUrlBuilder(config.host, config.dbName).getJdbcUrl)
      } yield {
        
        val hikariConfig = new HikariConfig()
        hikariConfig.setJdbcUrl(jdbcUrl)
        hikariConfig.addDataSourceProperty("useSSL", false)
        hikariConfig.setUsername(config.getUserName())
        hikariConfig.setPassword(config.getPassword())

        new HikariJdbcConfig(new HikariDataSource(hikariConfig), hikariConfig)
      }
    }
  }

  private def createConnection(ds: HikariJdbcConfig): Database = {
    Database.forSource(ds)
  }
  
}

case class BaseConfig(
  databaseName:  String,
  host:          String
)

case class MySQLJdbcConfigLoader(
  config: BaseConfig,
  loader: Config     = ConfigFactory.load()
) extends JdbcConfigLoader(config) {

  override val dataStoreName: String = "mysql"

}

abstract class JdbcConfigLoader(
  config: BaseConfig,
  loader: Config    = ConfigFactory.load()
) {

  protected val dataStoreName: String
  
  private val dataStoreProperty = dataStoreName       + "."
  private val databaseProperty  = config.databaseName + "."


  private val PATH_TO_USER      = dataStoreProperty + "user"
  private val PATH_TO_PASSWORD  = dataStoreProperty + "password"

  private val PATH_TO_HOST      = dataStoreProperty + databaseProperty + "host" + config.host
  private val PATH_TO_DB_NAME   = dataStoreProperty + databaseProperty + "databaseName"
  private val PATH_TO_PORT      = dataStoreProperty + databaseProperty + "port"

  lazy val host   = getHost()
  lazy val dbName = getDatabaseName()


  def getHost(): String =
    loader.getString(PATH_TO_HOST)

  def getDatabaseName(): String =
    loader.getString(PATH_TO_DB_NAME)

  def getUserName(): String =
    loader.getString(PATH_TO_USER)

  def getPassword(): String =
    loader.getString(PATH_TO_PASSWORD)

  def getPort(): Int =
    Try(loader.getInt(PATH_TO_PORT)).toOption.getOrElse(3306)
}