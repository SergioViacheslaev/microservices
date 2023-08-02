package com.marketplace.userdataservice.config

import com.marketplace.userdataservice.config.WebClientConfig.WebClientConstants.BASE_URL
import com.marketplace.userdataservice.config.WebClientConfig.WebClientConstants.TIMEOUT
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig {

    object WebClientConstants {
        const val BASE_URL = "https://random-data-api.com/api/v2"
        const val TIMEOUT = 1L
    }

    @Bean
    fun webClientWithTimeout(): WebClient {
        val httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(TIMEOUT))

        return WebClient.builder()
            .baseUrl(BASE_URL)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

}