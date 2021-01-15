package models

import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfig, HasDatabaseConfigProvider}
import play.api.mvc.{AbstractController, ControllerComponents}
import slick.jdbc._
import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{Await, ExecutionContext, Future}

class MovieRepository @Inject() (
                                  protected val dbConfigProvider: DatabaseConfigProvider,
                                  cc: ControllerComponents)(implicit ec: ExecutionContext)
  extends AbstractController(cc)
    with HasDatabaseConfigProvider[JdbcProfile] {
  private lazy val movieQuery = TableQuery[MovieTable]

  /**
   * Función de ayuda para crear la tabla si ésta
   * aún no existe en la base de datos.
   *
   * @return
   * @return
   * @return
   */
  def dbInit: Future[Unit] = {
    // Definición de la sentencia SQL de creación del schema
    db.run(movieQuery.schema.createIfNotExists)
  }
  def getAll = {
    val query= movieQuery.sortBy(_.id)
    db.run(query.result)
  }

  def getOne(id:String) = {
    val query= movieQuery.filter(_.id===id)
    db.run(query.result.headOption)

  }
  def create(movie:Movie) = {
    val insert= movieQuery += movie
    db.run(insert)
      .flatMap(_=> getOne(movie.id.getOrElse("")))


  }
  def update(id:String, movie:Movie) = {
    val query=movieQuery.filter(_.id===movie.id &&movie.id.contains(id) )
    val update= query.update(movie)
    db.run(update)
      .flatMap(_=> db.run(query.result.headOption))
  }
  def delete(id:String) = {
    val query= movieQuery.filter(_.id===id)
    for {
      objeto <- db.run(query.result.headOption)
      _<-db.run(query.delete)
    } yield objeto

  }

}