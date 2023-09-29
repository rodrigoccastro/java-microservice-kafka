package microservice.kafka.queue;

import microservice.kafka.Client;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class ServiceConsumer {

    KafkaConsumer consumer = new KafkaConsumer<String,Client>(getProperties());

    @Scheduled(fixedDelay = 10_000)
    public void execute() {

        consumer.subscribe(Collections.singletonList("CLIENT_SAVE"));
        ConsumerRecords<String,Client> records = consumer.poll(Duration.ofMillis(100));

        if (!records.isEmpty()) {
            System.out.println("Encontrei " + records.count() + " registros");
            for (ConsumerRecord record : records) {
                try {
                    System.out.println("-------------------------------------------");
                    System.out.println(record.key());
                    System.out.println(record.value());
                    System.out.println("salvar no mongo....");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, ServiceConsumer.class.getSimpleName());
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, Client.class.getName());
        properties.putAll(new HashMap<>());

        return properties;
    }

}

