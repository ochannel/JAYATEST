package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.exception.NotFoundException
import com.jayatest.monetaryconverter.model.ExchangeRatesApiResponse
import com.jayatest.monetaryconverter.model.MonetaryConverter
import com.jayatest.monetaryconverter.repository.MonetaryConverterRepository
import com.jayatest.monetaryconverterapi.model.MonetaryConverterDTO
import org.modelmapper.ModelMapper

import org.springframework.stereotype.Service

import java.math.RoundingMode
import java.time.LocalDateTime
import org.modelmapper.TypeToken;
import org.springframework.validation.Validator


@Service
class MonetaryConverterService(
    val exchangeRatesApiService: ExchangeRatesApiService,
    val monetaryConverterRepository: MonetaryConverterRepository,
     validator: Validator
) {

    fun create(monetaryConverterDTO: MonetaryConverterDTO): MonetaryConverterDTO {

        val turnsTypeOutPut = object : TypeToken<MonetaryConverterDTO>() {}.type
        val exchangeRatesApiResponse = exchangeRatesApiService.getRates();
        val monetaryConverter = MonetaryConverter().fillMonetaryConverter(monetaryConverterDTO);
        converterCurrency(monetaryConverter, exchangeRatesApiResponse)
        return ModelMapper().map(monetaryConverterRepository.save(monetaryConverter), turnsTypeOutPut)
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
                monetaryConverter.origenCurrencyValue?.divide(rateCurrencyInput?.toBigDecimal(), RoundingMode.HALF_UP)
                    ?.multiply(rateCurrencyOutput?.toBigDecimal())
            monetaryConverter.rate = rateCurrencyOutput?.toBigDecimal();
        };
    }

    fun listAll(): List<MonetaryConverterDTO> {
        val turnsType = object : TypeToken<List<MonetaryConverterDTO>>() {}.type
        return ModelMapper().map(monetaryConverterRepository.findAll(), turnsType);

    }

    fun findforidUsurious(idUsurious:Int?): List<MonetaryConverterDTO>{
        val turnsType = object : TypeToken<List<MonetaryConverterDTO>>() {}.type

        val listMonetaryConverter =monetaryConverterRepository.findforidUsurious(idUsurious)
        if(listMonetaryConverter.isEmpty()){
            throw NotFoundException("Não foi encontrado MonetaryConverter paro o usuário. ");
        }
        return ModelMapper().map(monetaryConverterRepository.findforidUsurious(idUsurious), turnsType);
    }



}