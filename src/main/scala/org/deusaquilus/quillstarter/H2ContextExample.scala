package org.deusaquilus.quillstarter

import java.io.Closeable
import javax.sql.DataSource

import io.getquill._
import org.h2.jdbcx.JdbcDataSource

object H2ContextExample {
  val datasoure = new JdbcDataSource()
  datasoure.setURL("jdbc:h2:mem:sample;INIT=RUNSCRIPT FROM 'src/main/resources/schema.sql'");
  datasoure.setUser("sa");
  datasoure.setPassword("sa");

  val context = new H2JdbcContext[Literal](Literal, datasoure.asInstanceOf[DataSource with Closeable])
  import context._

  case class Person(id:Int, firstName:String, lastName:String, age:Int)
  case class Address(personFk:Int, street:String, zip:Int)

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
