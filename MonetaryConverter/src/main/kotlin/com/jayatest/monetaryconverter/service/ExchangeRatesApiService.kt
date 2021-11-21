package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.model.ExchangeRatesApiResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ExchangeRatesApiService {

    fun getRates(): ExchangeRatesApiResponse? {

    val restTemplate = RestTemplate()
    val url="http://api.exchangeratesapi.io/v1/latest?access_key=f121cf6f67f62073bb995cd635e51b2d&base=EUR&symbols=BRL,USD,EUR,JPY&format=1"
    return restTemplate.getForObject(url,ExchangeRatesApiResponse().javaClass);
    }
}