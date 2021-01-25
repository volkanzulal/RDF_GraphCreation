import org.apache.jena.rdf.model.ModelFactory

object Main extends App {
  val model = ModelFactory.createDefaultModel()
  model.read("file:lumb1.ttl","TTL")

  println("rdf dataset size ="+model.size())
  val typeProperty = "https://www.w3.org/RDF/Validator/"

}
