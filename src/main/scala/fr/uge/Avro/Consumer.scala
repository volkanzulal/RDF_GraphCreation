package fr.uge.Avro
import org.apache.avro.Schema
import java.time.Duration
import java.util
import java.util.Properties

import com.twitter.bijection.Injection
import com.twitter.bijection.avro.GenericAvroCodecs
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

import scala.collection.mutable.ArrayBuffer

object Consumer {
  private val properties = new Properties()

  properties.put("bootstrap.servers", "localhost:9092")
  properties.put("group.id", "test")
  properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  properties.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer")

  val consumer : KafkaConsumer[String,Array[Byte]] = new KafkaConsumer[String,Array[Byte]](properties)

  var recordInjection: Injection[GenericRecord, Array[Byte]] = null

  def startConsumer(userSchema : Schema, topic : String) = {

    recordInjection = GenericAvroCodecs.toBinary(userSchema)
    consumer.subscribe(util.Arrays.asList(topic))
  }

  def getData() : ArrayBuffer[String] = {

    val data : ConsumerRecords[String,Array[Byte]] = consumer.poll(Duration.ofSeconds(2))

    val buffer = scala.collection.mutable.ArrayBuffer.empty[String]

    if(data.isEmpty){
      null
    }
    else{
      data.forEach( record => {
        val genRec : GenericRecord  = recordInjection.invert(record.value()).get
        buffer += genRec.toString
      })
      buffer
    }
  }

  def closeConsumer() = {
    consumer.close()
  }
}
