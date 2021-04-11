package fr.uge.Avro

import java.util.Properties

import com.twitter.bijection.Injection
import com.twitter.bijection.avro.GenericAvroCodecs
import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object Producer {
  private val properties = new Properties()
  val producer : KafkaProducer[String,Array[Byte]] = new KafkaProducer[String,Array[Byte]](properties)
  var recordInjection: Injection[GenericRecord, Array[Byte]] = null

  properties.put("bootstrap.servers", "localhost:9092")
  properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  properties.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer")

  def startProducer(userSchema : Schema) = {
    recordInjection = GenericAvroCodecs.toBinary(userSchema)
  }

  def produce(recordAvro : GenericData.Record , topic : String) = {
    try {
      val data : Array[Byte] = recordInjection.apply(recordAvro)
      val record = new ProducerRecord(topic, "key", data)
      producer.send(record)
    }
    catch {
      case e: Throwable => println(e)
    }
  }

  def closeProducer() = {
    producer.close()
  }
}
