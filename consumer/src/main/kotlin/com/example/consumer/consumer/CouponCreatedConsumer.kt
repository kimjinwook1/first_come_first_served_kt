package com.example.consumer.consumer

import com.example.consumer.domain.Coupon
import com.example.consumer.domain.FailedEvent
import com.example.consumer.repository.CouponRepository
import com.example.consumer.repository.FailedEventRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class CouponCreatedConsumer(
    private val couponRepository: CouponRepository,
    private val failedEventRepository: FailedEventRepository,
) {

    private val logger = LoggerFactory.getLogger(CouponCreatedConsumer::class.java)

    @KafkaListener(topics = ["coupon_create"], groupId = "group_1")
    fun listener(userId: Long) {
        kotlin.runCatching {
            couponRepository.save(Coupon(userId))
        }.onFailure { exception ->
            logger.error("error message: ${exception.message}")
            failedEventRepository.save(FailedEvent(userId))
        }
    }
}
