package org.deusaquilus.quillstarter.multicontext

import io.getquill.context.Context
import io.getquill.{Literal, MirrorSqlDialect, NamingStrategy, QuillSparkContext}
import io.getquill.context.jdbc.JdbcContext
import io.getquill.context.mirror.{MirrorDecoders, MirrorEncoders}
import io.getquill.context.sql.idiom.SqlIdiom
import io.getquill.dsl.EncodingDsl

trait HasSparkEncoders
  extends HasEncoders
  with QuillSparkContext with io.getquill.context.spark.Encoders with io.getquill.context.spark.Decoders

trait HasJdbcEncoders[Dialect <: SqlIdiom, Naming <: NamingStrategy]
  extends JdbcContext[Dialect, Naming]
  with HasEncoders
  with io.getquill.context.jdbc.Encoders with io.getquill.context.jdbc.Decoders

trait HasMirrorEncoders
  extends HasEncoders with Context[MirrorSqlDialect,Literal] with MirrorEncoders with MirrorDecoders

trait HasEncoders {
  this: EncodingDsl =>

  implicit val stringEncoder: Encoder[String]
  implicit val bigDecimalEncoder: Encoder[BigDecimal]
  implicit val booleanEncoder: Encoder[Boolean]
  implicit val byteEncoder: Encoder[Byte]
  implicit val shortEncoder: Encoder[Short]
  implicit val intEncoder: Encoder[Int]
  implicit val longEncoder: Encoder[Long]
  implicit val doubleEncoder: Encoder[Double]
}
