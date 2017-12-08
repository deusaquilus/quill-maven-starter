package org.deusaquilus.quillstarter.multicontext

import io.getquill.QuillSparkContext
import org.apache.spark.sql.SparkSession
import org.deusaquilus.Person
import org.deusaquilus.Schema._

object SparkExtendedContext
  extends QuillSparkContext with CrossCompatibleExtensions with HasSparkEncoders
  with io.getquill.context.spark.Encoders with io.getquill.context.spark.Decoders

object UsingSparkContext {
  import SparkExtendedContext._

  def main(args:Array[String]):Unit = {
    val spark = SparkSession.builder()
      .config("spark.debug.maxToStringFields", "200")
      .appName("SparkQuillExample")
      .master("local")
      .enableHiveSupport()
      .getOrCreate()

    implicit val sqlContext = spark.sqlContext
    import spark.implicits._

    // NOTE Be sure not to import Quill context again or import conflicts will happen
    //import QuillSparkContext._

    val peopleDf = people.toDS()

    val q = quote {
      val lq = liftQuery(peopleDf).filter(_.age > 10)
      filterByAge(lq, 10)
    }
    run(q).foreach(println(_))
  }
}
