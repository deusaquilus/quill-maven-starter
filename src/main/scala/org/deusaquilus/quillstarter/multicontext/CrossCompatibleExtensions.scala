package org.deusaquilus.quillstarter.multicontext

import io.getquill.context.Context
import org.deusaquilus.Person

trait CrossCompatibleExtensions {
  this: Context[_, _] with HasEncoders =>

  def filterByAge = quote {
    (q:Query[Person], age:Int) => q.filter(_.age > age)
  }
}
