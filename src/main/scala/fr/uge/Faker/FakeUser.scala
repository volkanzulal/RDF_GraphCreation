package fr.uge.Faker

import java.util.{Date, Locale}

import com.github.javafaker.Faker

case class FakeUser(
                     val id : Long,
                     val lastName : String,
                     val firstName : String,
                     val gender : String,
                     val zipcode : String,
                     val dateOfBirth : Date,
                     val vaccinated : Boolean,
                     val dateOfVaccine : Date,
                     val vaccineName : String,
                     val sideEffectCode : String,
                     val sideEffectName : String
                   )

object FakeUser {
  val faker = new Faker(new Locale("en-US"))
  var id = 0

  def getId(): Long = {
    id = id + 1
    id.toLong
  }

  def apply(gender : String , ageMin : Int , ageMax : Int ) =
    new FakeUser(
      getId(),faker.name().lastName(),faker.name().firstName(),gender,faker.address().zipCode(),
      faker.date().birthday(ageMin,ageMax), false, faker.date().birthday(), ""
      , "" , ""
    )

  def apply(gender : String , ageMin : Int , ageMax : Int , vaccineName : String ,
            dateVacMin : Date , dateVacMax : Date , sideEffect : (String,String)
           ) = new FakeUser(
    getId(),faker.name().lastName(),faker.name().firstName(),gender,faker.address().zipCode(),
    faker.date().birthday(ageMin,ageMax), true, faker.date().between(dateVacMin,dateVacMax),
    vaccineName , sideEffect._2 , sideEffect._1
  )
}
