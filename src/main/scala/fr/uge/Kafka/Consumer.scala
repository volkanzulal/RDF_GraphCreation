package fr.uge.Kafka

import java.io.FileWriter
import java.time.Duration
import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object Consumer{
  val properties = new Properties()
  val consumer : KafkaConsumer[String,String] = new KafkaConsumer[String,String](properties)

  properties.put("bootstrap.servers", "localhost:9092")
  properties.put("group.id", "consumer-group")
  properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  properties.put("enable.auto.commit", "true")
  properties.put("auto.commit.interval.ms", "1000")


  def subscribe(
                 topic : String
               ): Unit = {
    consumer.subscribe(util.Arrays.asList(topic))
  }

  def getData() : ConsumerRecords[String,String] = {
    val record = consumer.poll(Duration.ofSeconds(3))
    record
  }

  def getAllDataToJSONFile(path : String): Int = {
    val file = new FileWriter(path)
    file.write("[")
    while (true) {
      val records = consumer.poll(Duration.ofSeconds(5))
      records.forEach( s => {
        if(s.value().equals("EOF")){
          file.write("{}]")
          file.close()
          return 1
        }
        file.write(s.value() + ",\n")
      })
    }
    return 0
  }

  def closeConsumer() = {
    consumer.close()
  }
}
