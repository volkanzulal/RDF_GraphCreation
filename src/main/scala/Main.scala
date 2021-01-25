import org.apache.jena.rdf.model.ModelFactory

object Main extends App {
  val model = ModelFactory.createDefaultModel()
  model.read("file:lumb1.ttl","TRIPLE")

  println("rdf dataset size ="+model.size())
  val typeProperty = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type"


  def typePropertySize() = {
    val rdfType = model.createProperty(typeProperty)
    val iterator = model.listSubjectsWithProperty(rdfType)
    iterator.toList.size
  }

  def numberOfDrugs()= {
    val rdfType = model.createProperty(typeProperty)
    val obj = model.createResource("http://www4.wiwiss.fu-berlin.de/drugbank/resource/drugbank/drugs")
    model.listSubjectsWithProperty(rdfType,obj).toList.size
  }

  println("number of triples with an rdf:type property =" + typePropertySize)
  println("number of drugs ="+ numberOfDrugs)
}
