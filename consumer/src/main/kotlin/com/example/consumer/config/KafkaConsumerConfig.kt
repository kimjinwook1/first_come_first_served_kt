package com.example.consumer.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory

@Configuration
class KafkaConsumerConfig {

    @Bean
    fun consumerFactory(): ConsumerFactory<String, Long> {
        val config = hashMapOf<String, Any>(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
            ConsumerConfig.GROUP_ID_CONFIG to "group_id",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to LongDeserializer::class.java
        )

        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Long> {
        return ConcurrentKafkaListenerContainerFactory<String, Long>().apply {
            consumerFactory = consumerFactory()
        }
    }
}
