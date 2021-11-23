package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.exception.ExchangeRatesApiException
import com.jayatest.monetaryconverter.model.ExchangeRatesApiResponse
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ExchangeRatesApiService : Logging {

    fun getRates(): ExchangeRatesApiResponse? {

        try {
            val restTemplate = RestTemplate()
            val url ="http://api.exchangeratesapi.io/v1/latest?access_key=f121cf6f67f62073bb995cd635e51b2d&base=EUR&symbols=BRL,USD,EUR,JPY&format=1"
            val retornExchangeRatesApi = restTemplate.getForObject(url, ExchangeRatesApiResponse().javaClass)
            return retornExchangeRatesApi;
        }catch ( e: Exception ){
          logger.error("Error ao chamar serviço ExchangeRatesApi,".plus(e.message))
          throw  ExchangeRatesApiException(e.message)

        }
    }
}