package org.deusaquilus.quillstarter.issueexamples

import io.getquill._
import org.deusaquilus.quillstarter.Util
import org.deusaquilus.{Address, Person}

object H2ContextExample {

  val context = new H2JdbcContext[Literal](Literal, Util.datasource)
  import context._

  def main(args:Array[String]):Unit = {
//    def simpleAgeFilter = quote {
//      (q:Query[Person], age:Int) => q.filter(_.age > lift(age))
//    }
    def simpleAgeFilter = quote {
      (q:Query[Person], age:Int) => q.filter(_.age > age)
    }
    run({simpleAgeFilter(query[Person], 10)}).foreach(println(_))
  }
}
