package com.example.consumer.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class FailedEvent(
    var userId: Long,
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var Id :Long = 0L
}