import java.io.{FileOutputStream, StringWriter}

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.apache.jena.rdf.model.{Model, ModelFactory, StmtIterator}
import fr.uge.Faker.FakeDataAdd.{addFakeData, getData, getGender, getVaccine, setGender, setVaccine}
import fr.uge.Faker.FakeUser
import fr.uge.Kafka.{Consumer, Producer}
import kafka.tools.StateChangeLogMerger.topic

import scala.jdk.CollectionConverters.IteratorHasAsScala
import scala.util.Random

object MainKafka extends App {
  val model = ModelFactory.createDefaultModel()
  val modelLumb = model.read("file:lumb1.ttl","TTL")
  val nTriples = modelLumb.size()
  println("Nombre de Triples  ="+ nTriples)

  val typeProperty = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"

  val vaccineListPercent = List(
    ("Pfizer",20),
    ("Moderna",20),
    ("AstraZeneca",20),
    ("SpoutnikV",20),
    ("CanSinoBio",20)
  )

  val typeUsers = List(
    "AssistantProfessor",
    "AssociateProfessor",
    "FullProfessor",
    "GraduateStudent",
    "Lecturer",
    "UndergraduateStudent"
  )

  val SideEffectList = List (
    ("Injection site pain","C015828"),
    ("fatigue","C0015672"),
    ("headache","C0018681"),
    ("Muscle pain","C0231528"),
    ("chills","C0085593"),
    ("Joint pain","C0003862"),
    ("fever","C0015967"),
    ("Injection site swelling","C0151605"),
    ("Injection site redness","C0852625"),
    ("Nausea","C0027497"),
    ("Malaise","C0231218"),
    ("Lymphadenopathy","C0497156"),
    ("Injection site tenderness","C0863083")
  )

  val resourceType = "http://swat.cse.lehigh.edu/onto/univ-bench.owl#"



  def allUserNotShuffled(data : List[String]):List[(String,String)] = {data.map(user => {
    val newResourceType = resourceType + user
    getData(model, typeProperty+"type", newResourceType).asScala.toList.map(
      {x => (user,x.toString)}).distinct
  }).foldLeft(List[(String,String)]())((acc,el) => List.concat(acc,el))}

  val allUserShuffled  : List[(String,String)] = Random.shuffle(allUserNotShuffled(typeUsers))

  setGender(allUserShuffled.size,48)
  setVaccine(allUserShuffled.size,30,vaccineListPercent)

  val map = JsonMapper.builder().addModule(DefaultScalaModule).build()

  Consumer.subscribe("test")

  val topic = "test"

  def vaccinedToJSON(user : FakeUser){
    if(user.vaccinated){
      val test = new StringWriter
      map.writeValue(test, user)
      Producer.produce(topic, test.toString)
    }
  }

  allUserShuffled.foreach {
    case ("AssistantProfessor", resource) => {
      val user = addFakeData(model, typeProperty, resource, 25, 50, getGender(), getVaccine(), SideEffectList)
      vaccinedToJSON(user)
    }
    case ("AssociateProfessor", resource) => {
      val user = addFakeData(model, typeProperty, resource, 30, 70, getGender(), getVaccine(), SideEffectList)
      vaccinedToJSON(user)
    }
    case ("FullProfessor", resource) => {
      val user = addFakeData(model, typeProperty, resource, 30, 70, getGender(), getVaccine(), SideEffectList)
      vaccinedToJSON(user)
    }
    case ("GraduateStudent", resource) => {
      val user = addFakeData(model, typeProperty, resource, 25, 30, getGender(), getVaccine(), SideEffectList)
      vaccinedToJSON(user)
    }
    case ("Lecturer", resource) => {
      val user = addFakeData(model, typeProperty, resource, 20, 30, getGender(), getVaccine(), SideEffectList)
      vaccinedToJSON(user)
    }
    case ("UndergraduateStudent", resource) => {
      val user = addFakeData(model, typeProperty, resource, 20, 25, getGender(), getVaccine(), SideEffectList)
      vaccinedToJSON(user)
    }
  }

  Producer.sendEndSignal(topic)

  Consumer.getAllDataToJSONFile("vaccinedLumb.json")

  val outFile : FileOutputStream = new FileOutputStream(
    "lubm1_sideEffect.ttl"
  )
  model.write(outFile)
  Producer.closeProducer()
  Consumer.closeConsumer()
}
