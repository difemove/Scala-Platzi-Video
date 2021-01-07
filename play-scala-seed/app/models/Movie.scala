package models
import java.util.UUID



import slick.jdbc.SQLiteProfile.api._
import slick.lifted.ProvenShape
/*
* Se debe importar el contenido del objeto api que está en el profile de nuestra base de datos SQLite (en Scala el guión bajo _ en una importación, es equivalente al asterisco * de Java o Python).
*La clase extiende de un trait llamado Table[T] que recibe un tipo de dato T, que en este caso será el case class Movie. Allí también de le dice el nombre de la tabla de SQL a la que hace referencia.
*Cada atributo se define con un tipo column, dándole el tipo de dato de Scala, y el nombre de la columna en SQL.
*Algunos atributos tienen opciones especiales, como el id que tiene O.PrimaryKey, o tags que tiene O.Length(2000, varying = true) . Existen varias opciones que están dentro del objeto O de opciones de columna. Las opciones disponibles siempre depende de la base de datos que estés usando.
*La definición del id en la tabla es directamente un String en vez de un Option[String]. Esto es porque la clave primaria nunca puede ser nula.
*Hay una definición especial que describe la proyección entre el objeto tabla y la case class a la que mapea. El nombre que tiene es un asterisco *. Para el atributo id.?, ese ? es necesario porque en la definición de la tabla ese atributo es obligatorio, pero en la definición del objeto es opcional, de esa manera el compilador sabe que lo debe transformar.
*
* */
class MovieTable (tag:Tag) extends Table[Movie] (tag, "movie" ){
  def id: Rep[String] = column[String]("id", O.PrimaryKey)
  def title: Rep[String] = column[String] ("title")
  def year: Rep[Int] = column[Int]("year")
  def cover: Rep[String] = column[String]("cover")
  def description : Rep[String] = column[String]("description")
  def duration: Rep[Int] = column[Int]("duration")
  def contentRating: Rep[String] = column[String]("content_rating")
  def source: Rep[String] = column[String]("source")
  def tags:Rep[Option[String]] = column[Option[String]]("tags", O.Length(2000, varying=true))

  def * =
    (id.?,title,year,cover,description,duration,contentRating,source,tags) <> (Movie.tupled,Movie.unapply)

}



  case class Movie(

                    id: Option[String] = Option(UUID.randomUUID.toString),
                    title: String,
                    year: Int,
                    cover: String,
                    description: String,
                    duration: Int,
                    contentRating: String,
                    source: String,
                    tags: Option[String]


                  )

