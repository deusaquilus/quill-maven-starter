package org.deusaquilus.quillstarter

import java.io.Closeable
import javax.sql.DataSource

import io.getquill._
import org.deusaquilus.{Address, Person}

object H2ContextExample {

  val context = new H2JdbcContext[Literal](Literal, Util.datasource)
  import context._

  def main(args:Array[String]):Unit = {
    val simpleQuery = quote { query[Person].filter(_.age > 10) }
    run(simpleQuery).foreach(println(_))

    val joinedQueryTupleOutput = quote {
      for {
        p <- query[Person]
        a <- query[Address] if p.id == a.personFk
      } yield (p.firstName, p.lastName, a.street)
    }
    run(joinedQueryTupleOutput).foreach(println(_))

    val joinedQueryPairOutput = quote {
      for {
        p <- query[Person]
        a <- query[Address] if p.id == a.personFk
      } yield (p, a)
    }
    run(joinedQueryPairOutput).foreach(println(_))

    case class AdHocTuple(firstName:String, lastName:String, street:String, zip:Int)
    val addHocTupleOutput = quote {
      for {
        p <- query[Person]
        a <- query[Address] if p.id == a.personFk
      } yield AdHocTuple(p.firstName, p.lastName, a.street, a.zip)
    }
    run(addHocTupleOutput).foreach(println(_))
  }
}
