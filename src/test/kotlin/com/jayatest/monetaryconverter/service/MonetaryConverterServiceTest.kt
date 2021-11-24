package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.exception.ExchangeRatesApiException
import com.jayatest.monetaryconverter.exception.NotFoundException
import com.jayatest.monetaryconverter.model.Currency
import com.jayatest.monetaryconverter.model.ExchangeRatesApiResponse
import com.jayatest.monetaryconverter.model.MonetaryConverter
import com.jayatest.monetaryconverter.repository.MonetaryConverterRepository
import com.jayatest.monetaryconverterapi.model.MonetaryConverterDTO
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

internal class MonetaryConverterServiceTest {



    private val mockExchangeRatesApiService = Mockito.mock(ExchangeRatesApiService::class.java)
    private val mockMonetaryConverterRepository = Mockito.mock(MonetaryConverterRepository::class.java)
    private val monetaryConverterService:MonetaryConverterService= MonetaryConverterService(mockExchangeRatesApiService,mockMonetaryConverterRepository);

    @Test
    fun createSuccess() {
        val monetaryConverter = MonetaryConverter();
        fillMonetaryConverter(monetaryConverter)
        var monetaryConverterDTO = MonetaryConverterDTO();
        fillMonetaryConverterDTO(monetaryConverterDTO)
        var exchangeRatesApiResponse = ExchangeRatesApiResponse()
        fillExchangeRatesApiResponse(exchangeRatesApiResponse)
        Mockito.`when`(mockExchangeRatesApiService.getRates()).thenReturn(exchangeRatesApiResponse);
        Mockito.`when`(mockMonetaryConverterRepository.save(Mockito.any())).thenReturn(monetaryConverter);
        var retornCreate = monetaryConverterService.create(monetaryConverterDTO);
        assert(retornCreate.destinationCurrency!=null)
    }

    @Test
    fun createError() {
        val expectedMessage = "I/O error on GET request"
        val monetaryConverter = MonetaryConverter();
        fillMonetaryConverter(monetaryConverter)
        var monetaryConverterDTO = MonetaryConverterDTO();
        fillMonetaryConverterDTO(monetaryConverterDTO)
        var exchangeRatesApiResponse = ExchangeRatesApiResponse()
        fillExchangeRatesApiResponse(exchangeRatesApiResponse)
        Mockito.`when`(mockExchangeRatesApiService.getRates()).thenThrow(ExchangeRatesApiException(expectedMessage))

        val exception: ExchangeRatesApiException = assertThrows(ExchangeRatesApiException::class.java) {
            monetaryConverterService.create(monetaryConverterDTO);
        }
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains(expectedMessage))
    }
    @Test
    fun listAllSuccess(){
        val monetaryConverter1 = MonetaryConverter();
        val monetaryConverter2 = MonetaryConverter();
        fillMonetaryConverter(monetaryConverter1)
        fillMonetaryConverter(monetaryConverter2)
        monetaryConverter2.idUsurious=2
        Mockito.`when`(mockMonetaryConverterRepository.findAll()).thenReturn(listOf(monetaryConverter1,monetaryConverter2))
        assert(monetaryConverterService.listAll().size==2);
    }
    @Test
    fun listAllEmpty(){
        Mockito.`when`(mockMonetaryConverterRepository.findAll()).thenReturn(listOf())
        assert(monetaryConverterService.listAll().isEmpty());

    }
    @Test
    fun findforidUsuriousSucess(){
        val monetaryConverter1 = MonetaryConverter();
        val monetaryConverter2 = MonetaryConverter();
        fillMonetaryConverter(monetaryConverter1)
        fillMonetaryConverter(monetaryConverter2)
        monetaryConverter2.origenCurrency=Currency.BRL
        monetaryConverter2.origenCurrencyValue= BigDecimal(5.00);
        Mockito.`when`(mockMonetaryConverterRepository.findforidUsurious(1)).thenReturn(listOf(monetaryConverter1,monetaryConverter2))

        var listMonetaryConverter = monetaryConverterService.findforidUsurious(1);
        assert(listMonetaryConverter[0].idUsurious.equals("1"));
        assert(listMonetaryConverter[1].idUsurious.equals("1"));

    }

    @Test
    fun findforidUsuriousNotFound(){
        Mockito.`when`(mockMonetaryConverterRepository.findforidUsurious(1)).thenReturn(listOf())

        val exception: NotFoundException = assertThrows(NotFoundException::class.java) {
            monetaryConverterService.findforidUsurious(7);

        }
        val actualMessage = exception.message
        assertTrue(actualMessage!!.contains("Não foi encontrado MonetaryConverter"))
    }



    private fun fillMonetaryConverter(monetaryConverter: MonetaryConverter) {
        monetaryConverter.rate = BigDecimal(1.125049);
        monetaryConverter.currentDate = LocalDateTime.now()
        monetaryConverter.destinationCurrencyValue = BigDecimal(7.875343)
        monetaryConverter.transactionId = "619d7dbba969e727ee0fa1eb"
        monetaryConverter.destinationCurrency = Currency.USD
        monetaryConverter.idUsurious = 1
        monetaryConverter.origenCurrencyValue = BigDecimal(7.00)
        monetaryConverter.origenCurrency = Currency.EUR
    }

    private fun fillMonetaryConverterDTO(monetaryConverterDTO: MonetaryConverterDTO) {
        monetaryConverterDTO.idUsurious = "1"
        monetaryConverterDTO.origenCurrency = "EUR"
        monetaryConverterDTO.origenCurrencyValue = 1.00;
        monetaryConverterDTO.destinationCurrency = "USD"
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

}