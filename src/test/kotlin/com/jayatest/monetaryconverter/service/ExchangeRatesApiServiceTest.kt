package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.exception.ExchangeRatesApiException
import com.jayatest.monetaryconverter.model.ExchangeRatesApiResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.web.client.RestTemplate


class ExchangeRatesApiServiceTest {




    private val mockRestTemplate: RestTemplate = Mockito.mock(RestTemplate::class.java)
    private val testExchangeRatesApiService: ExchangeRatesApiService = ExchangeRatesApiService(mockRestTemplate)
    private val url =
        "http://api.exchangeratesapi.io/v1/latest?access_key=f121cf6f67f62073bb995cd635e51b2d&base=EUR&symbols=BRL,USD,EUR,JPY&format=1"

    @Test
    public fun getRatesSuccess() {

        var exchangeRatesApiResponse = ExchangeRatesApiResponse()
        fillExchangeRatesApiResponse(exchangeRatesApiResponse)
        Mockito.`when`(mockRestTemplate.getForObject(url, ExchangeRatesApiResponse().javaClass)).thenReturn(exchangeRatesApiResponse);
        assertEquals(testExchangeRatesApiService.getRates()!!.success, true);
    }

    private fun fillExchangeRatesApiResponse(exchangeRatesApiResponse: ExchangeRatesApiResponse) {
        exchangeRatesApiResponse.success = true
        exchangeRatesApiResponse.timestamp = 1637708044L;
        exchangeRatesApiResponse.base = "EUR";
        exchangeRatesApiResponse.date = "2021-11-23"
        val rates = HashMap<String, Double>()
        rates["BRL"] = 6.268661;
        rates["USD"] = 1.125049;
        rates["EUR"] = 1.00;
        rates["JPY"] = 129.503815;
        exchangeRatesApiResponse.rates = rates;
    }

    @Test
    public fun getRatesError() {
        val expectedMessage = "I/O error on GET request"
        Mockito.`when`(mockRestTemplate.getForObject(url, ExchangeRatesApiResponse().javaClass)).thenThrow(ExchangeRatesApiException(expectedMessage))
        val exception: ExchangeRatesApiException = assertThrows(ExchangeRatesApiException::class.java) {
            testExchangeRatesApiService.getRates()
        }
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }


}