package fr.uge.Faker

import java.util.Date

import org.apache.jena.rdf.model.{Model, ResIterator, Resource, Statement}

object FakeDataAdd {

  def getStatementFromUserFirstName(model : Model , resource : Resource, property : String,
                                    user: FakeUser
                                   ) : Statement = {
    val p = model.createProperty(property+"firstName")
    val o = user.firstName
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserLastName(model : Model , resource : Resource, property : String,
                                   user: FakeUser
                                  ) : Statement = {
    val p = model.createProperty(property+"lastName")
    val o = user.lastName
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserId(model : Model , resource : Resource, property : String,
                             user: FakeUser
                            ) : Statement = {
    val p = model.createProperty(property+"id")
    val o = user.id.toString
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserGender(model : Model , resource : Resource, property : String,
                                 user: FakeUser
                                ) : Statement = {
    val p = model.createProperty(property+"gender")
    val o = user.gender
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserZipCode(model : Model , resource : Resource, property : String,
                                  user: FakeUser
                                 ) : Statement = {
    val p = model.createProperty(property+"zipCode")
    val o = user.zipcode
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserBirthDay(model : Model , resource : Resource, property : String,
                                   user: FakeUser
                                  ) : Statement = {
    val p = model.createProperty(property+"birthDay")
    val o = user.dateOfBirth.toString
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserVaccineState(model : Model , resource : Resource, property : String,
                                       user: FakeUser
                                      ) : Statement = {
    val p = model.createProperty(property+"vaccineState")
    val o = user.vaccinated.toString
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserVaccineDate(model : Model , resource : Resource, property : String,
                                      user: FakeUser
                                     ) : Statement = {
    val p = model.createProperty(property+"vaccineDate")
    val o = user.dateOfVaccine.toString
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserVaccineName(model : Model , resource : Resource, property : String,
                                      user: FakeUser
                                     ) : Statement = {
    val p = model.createProperty(property+"vaccineName")
    val o = user.vaccineName
    model.createStatement(resource,p,o)
  }

  def getStatementFromUserVaccineSideEffectName(model : Model , resource : Resource, property : String,
                                                user: FakeUser
                                               ) : Statement = {
    val p = model.createProperty(property+"sideEffectName")
    val o = user.sideEffectName
    model.createStatement(resource,p,o)

  }

  def getStatementFromUserVaccineSideEffectCode(model : Model , resource : Resource, property : String,
                                                user: FakeUser
                                               ) : Statement = {
    val p = model.createProperty(property+"sideEffectCode")
    val o = user.sideEffectCode
    model.createStatement(resource,p,o)

  }

  def  getData(model : Model , typeProperty : String , resource : String) : ResIterator = {
    val rdfType = model.createProperty(typeProperty);
    val obj = model.createResource(resource)
    model.listSubjectsWithProperty(rdfType,obj)
  }
  def addFakeData(model : Model, property : String, resource : String,
                  ageMin : Int , ageMax : Int , gender : String,
                  vaccine : ( Boolean,String,Date) , sideEffect : List[(String,String)]
                 ): FakeUser ={

    val s1 = model.createResource(resource)

    if(vaccine._1){
      val rand = scala.util.Random
      val vaccineSideEffect = sideEffect(rand.nextInt(sideEffect.length-1))
      val user = FakeUser(gender,ageMin,ageMax,vaccine._2,vaccine._3,vaccine._3,vaccineSideEffect)
      model.add(getStatementFromUserFirstName(model,s1,property,user))
      model.add(getStatementFromUserLastName(model,s1,property,user))
      model.add(getStatementFromUserId(model,s1,property,user))
      model.add(getStatementFromUserGender(model,s1,property,user))
      model.add(getStatementFromUserZipCode(model,s1,property,user))
      model.add(getStatementFromUserBirthDay(model,s1,property,user))
      model.add(getStatementFromUserVaccineState(model,s1,property,user))
      model.add(getStatementFromUserVaccineName(model,s1,property,user))
      model.add(getStatementFromUserVaccineDate(model,s1,property,user))
      model.add(getStatementFromUserVaccineSideEffectName(model,s1,property,user))
      model.add(getStatementFromUserVaccineSideEffectCode(model,s1,property,user))
      user
    }
    else{
      val user = FakeUser(gender,ageMin,ageMax)
      model.add(getStatementFromUserFirstName(model,s1,property,user))
      model.add(getStatementFromUserLastName(model,s1,property,user))
      model.add(getStatementFromUserId(model,s1,property,user))
      model.add(getStatementFromUserGender(model,s1,property,user))
      model.add(getStatementFromUserZipCode(model,s1,property,user))
      model.add(getStatementFromUserBirthDay(model,s1,property,user))
      model.add(getStatementFromUserVaccineState(model,s1,property,user))
      user
    }
  }

  var cptGender = 0
  var genderSizeBeforeChange = 0
  def setGender(peopleSize : Int , genderOnePercentage : Int ): Unit = {
    genderSizeBeforeChange = (peopleSize * genderOnePercentage) / 100
  }

  def getGender(): String ={
    if(cptGender < genderSizeBeforeChange) {
      cptGender += 1
      "Male"
    } else {
      cptGender += 1
      "Female"
    }
  }

  var vaccineCpt = 0
  var vaccineSizeBeforeChange = 0
  var vaccineNameBeforeChange = 0
  var actualVaccine : (String,Int) = ("empty",1)
  var vaccinePercent : List[(String,Int)] = List()

  def setVaccine(peopleSize : Int, vaccinePercentage : Int , vaccinePercentByName : List[(String,Int)] ): Unit = {
    vaccineSizeBeforeChange = (peopleSize * vaccinePercentage) / 100
    vaccinePercent = vaccinePercentByName.map( x => (x._1, (vaccineSizeBeforeChange * x._2)/100))
    actualVaccine =  vaccinePercent.head
    vaccinePercent = vaccinePercent.drop(1)
  }

  def getVaccine(): (Boolean,String,Date) = {
    if( vaccineCpt < vaccineSizeBeforeChange){
      // send a vaccine
      if( vaccineNameBeforeChange > actualVaccine._2){
        // change vaccine
        vaccineNameBeforeChange = 0
        actualVaccine = vaccinePercent.head
        vaccinePercent = vaccinePercent.drop(1)
      }
      vaccineNameBeforeChange = vaccineNameBeforeChange + 1
      vaccineCpt = vaccineCpt + 1

      (true,actualVaccine._1,new Date())

    }
    else{
      (false,"",new Date())
    }
  }
}
