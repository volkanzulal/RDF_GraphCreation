package fr.uge.Kafka

import java.util.Properties

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object Producer {
  val properties = new Properties()
  val producer : KafkaProducer[String,String] = new KafkaProducer[String,String](properties)

  properties.put("bootstrap.servers", "localhost:9092")
  properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  def produce(topic : String, value : String) = {
    producer.send(new ProducerRecord(topic,"key",value))
  }

  def sendEndSignal(topic : String): Unit ={
    producer.send(new ProducerRecord(topic,"key","EOF"))
  }

  def closeProducer(): Unit ={
    producer.close()
  }
}
