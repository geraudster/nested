package com.zenika.customerproducer;

import com.zenika.testavro.CommonHeader;
import com.zenika.testavro.Customer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class CustomerProducer {
    public static void main(String[] args) {
        System.out.println(">>> Starting Sample Avro Producer Application");

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://schema-registry:8081");

        try (KafkaProducer<String, Customer> producer = new KafkaProducer<>(props)) {
            Customer customer = Customer.newBuilder()
                    .setCustomerName("Foo")
                    .setHeader(CommonHeader.newBuilder().setKey("A header key").setValue("A header value").build())
                    .build();
            ProducerRecord<String, Customer> record = new ProducerRecord<>("customer", customer);
            RecordMetadata recordMetadata = producer.send(record).get();
            System.out.println(recordMetadata.toString());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
