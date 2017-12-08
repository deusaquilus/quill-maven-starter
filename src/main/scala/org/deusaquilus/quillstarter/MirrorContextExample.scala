package org.deusaquilus.quillstarter

import io.getquill._
import org.deusaquilus.{Address, Person}

object MirrorContextExample {
  val context = new SqlMirrorContext(MirrorSqlDialect, Literal)
  import context._

  def main(args:Array[String]):Unit = {
    val simpleQuery = quote { query[Person].filter(_.age > 10) }
    println(run(simpleQuery).string)

    val joinedQueryTupleOutput = quote {
      for {
        p <- query[Person]
        a <- query[Address] if p.id == a.personFk
      } yield (p.firstName, p.lastName, a.street)
    }
    println(run(joinedQueryTupleOutput).string)

    val joinedQueryPairOutput = quote {
      for {
        p <- query[Person]
        a <- query[Address] if p.id == a.personFk
      } yield (p, a)
    }
    println(run(joinedQueryPairOutput).string)

    case class AdHocTuple(firstName:String, lastName:String, street:String, zip:Int)
    val addHocTupleOutput = quote {
      for {
        p <- query[Person]
        a <- query[Address] if p.id == a.personFk
      } yield AdHocTuple(p.firstName, p.lastName, a.street, a.zip)
    }
    println(run(addHocTupleOutput).string)
  }
}
