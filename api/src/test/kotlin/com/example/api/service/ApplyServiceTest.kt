package com.example.api.service

import com.example.api.repository.CouponRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class ApplyServiceTest @Autowired constructor(
    private val applyService: ApplyService,
    private val couponRepository: CouponRepository
) {

    @Test
    fun 한번만응모() {
        applyService.apply(1L)

        val count = couponRepository.count()

        assertThat(count).isEqualTo(1)
    }

    @Test
    fun 여러명응모() {
        val threadCount = 1000
        val newFixedThreadPool = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 0 until threadCount) {
            val userId = i.toLong()
            newFixedThreadPool.submit {
                try {
                    applyService.apply(userId)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        Thread.sleep(10000)

        val count = couponRepository.count()
        assertThat(count).isEqualTo(100)
    }

    @Test
    fun 한명당_한개의쿠폰만_발급() {
        val threadCount = 1000
        val newFixedThreadPool = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount)

        for (i in 0 until threadCount) {
            val userId = i.toLong()
            newFixedThreadPool.submit {
                try {
                    applyService.apply(1L)
                } finally {
                    latch.countDown()
                }
            }
        }
        latch.await()

        Thread.sleep(10000)

        val count = couponRepository.count()
        assertThat(count).isEqualTo(1)
    }

}