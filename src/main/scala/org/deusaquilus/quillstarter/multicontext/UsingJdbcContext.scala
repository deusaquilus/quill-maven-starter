package org.deusaquilus.quillstarter.multicontext

import java.io.Closeable
import javax.sql.DataSource

import io.getquill.{H2Dialect, H2JdbcContext, Literal, NamingStrategy}
import org.deusaquilus.Person
import org.deusaquilus.quillstarter.Util

class H2ExtendedContext[N <: NamingStrategy](
  namingStrategy: N,
  dataSource: DataSource with Closeable
) extends H2JdbcContext[N](namingStrategy, dataSource) with CrossCompatibleExtensions with HasJdbcEncoders[H2Dialect, N]

object UsingJdbcContext {
  val context = new H2ExtendedContext[Literal](Literal, Util.datasource)
  import context._

  def main(args:Array[String]):Unit = {
    val q = quote { filterByAge(query[Person], 10) }
    run(q).foreach(println(_))
  }
}
