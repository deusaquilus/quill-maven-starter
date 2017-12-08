package org.deusaquilus

case class Person(id:Int, firstName:String, lastName:String, age:Int)
case class Address(personFk:Int, street:String, zip:Int)

object Schema {
  val people = Seq(
    Person(1, "Joe", "Bloggs", 22),
    Person(2, "Jack", "Ripper", 33)
  )
  val addresses = Seq(
    Address(1, "123 Someplace", 1001),
    Address(1, "678 Blah", 2002),
    Address(2, "111234 Some Other Place", 3333)
  )
}
