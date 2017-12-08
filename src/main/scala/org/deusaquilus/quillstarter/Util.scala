package org.deusaquilus.quillstarter

import java.io.Closeable
import javax.sql.DataSource

import org.h2.jdbcx.JdbcDataSource

object Util {
  def datasource = {
    val datasoure = new JdbcDataSource()
    datasoure.setURL("jdbc:h2:mem:sample;INIT=RUNSCRIPT FROM 'src/main/resources/schema.sql'")
    datasoure.setUser("sa")
    datasoure.setPassword("sa")
    datasoure.asInstanceOf[DataSource with Closeable]
  }
}
