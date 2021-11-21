package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.model.ExchangeRatesApiResponse
import com.jayatest.monetaryconverter.model.MonetaryConverter
import com.jayatest.monetaryconverter.repository.MonetaryConverterRepository
import org.springframework.stereotype.Service
import java.math.RoundingMode
import java.time.LocalDateTime


@Service
class MonetaryConverterService(
    val exchangeRatesApiService: ExchangeRatesApiService,
    val monetaryConverterRepository: MonetaryConverterRepository
) {

    fun create(monetaryConverter: MonetaryConverter): MonetaryConverter {
        val exchangeRatesApiResponse = exchangeRatesApiService.getRates();
        converterCurrency(monetaryConverter, exchangeRatesApiResponse)
        return monetaryConverterRepository.save(monetaryConverter)
    }

    private fun converterCurrency(
        monetaryConverter: MonetaryConverter,
        exchangeRatesApiResponse: ExchangeRatesApiResponse?
    ) {
        monetaryConverter.currentDate = LocalDateTime.now();
        if (exchangeRatesApiResponse != null) {
            val rateCurrencyInput = exchangeRatesApiResponse.rates?.get(monetaryConverter.origenCurrency.toString())
            val rateCurrencyOutput =
                exchangeRatesApiResponse.rates?.get(monetaryConverter.destinationCurrency.toString())

            monetaryConverter.destinationCurrencyValue =
                monetaryConverter.origenCurrencyValue.divide(rateCurrencyInput?.toBigDecimal(), RoundingMode.HALF_UP)
                    .multiply(rateCurrencyOutput?.toBigDecimal())
            monetaryConverter.rate=rateCurrencyOutput?.toBigDecimal();
        };
    }

    fun listAll() = monetaryConverterRepository.findAll();
}