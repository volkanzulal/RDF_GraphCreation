import java.io.{FileWriter, IOException}

import org.apache.jena.rdf.model.{ModelFactory, StmtIterator}

object Main extends App {
  val model = ModelFactory.createDefaultModel()
  val modelLumb = model.read("file:lumb1.ttl","TTL")
  val nTriples = modelLumb.size()


  println("Nombre de Triples  ="+ nTriples)
  val typeProperty = "https://www.w3.org/RDF/Validator/"
  val property = modelLumb.createProperty("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")
  val it = modelLumb.listObjectsOfProperty(property)
  val propertyList = it.toList

  propertyList.forEach(println)

  val iter: StmtIterator = model.listStatements
  var count = 0

  while (iter.hasNext) {
    val stmt = iter.nextStatement
    val subject = stmt.getSubject
    val predicate = stmt.getPredicate
    val `object` = stmt.getObject
    System.out.print(subject.toString)
    System.out.print(" " + predicate.toString + " ")
    System.out.print(" " + `object`.toString + " ")
  }

  def writeRDF(): Unit = {
    val fileName = "lubm1ex.ttl"
    val out = new FileWriter(fileName)
    try model.write(out, "N-TRIPLE")
    finally try out.close()
    catch {
      case _: IOException => // ignore
    }
  }
  writeRDF()


}
