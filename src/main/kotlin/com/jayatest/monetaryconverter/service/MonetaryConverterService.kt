package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.exception.NotFoundException
import com.jayatest.monetaryconverter.model.ExchangeRatesApiResponse
import com.jayatest.monetaryconverter.model.MonetaryConverter
import com.jayatest.monetaryconverter.repository.MonetaryConverterRepository
import com.jayatest.monetaryconverter.validation.IntegerValidation
import com.jayatest.monetaryconverterapi.model.MonetaryConverterDTO
import org.modelmapper.ModelMapper

import org.springframework.stereotype.Service

import java.math.RoundingMode
import java.time.LocalDateTime
import org.modelmapper.TypeToken;


@Service
class MonetaryConverterService(
    val exchangeRatesApiService: ExchangeRatesApiService,
    val monetaryConverterRepository: MonetaryConverterRepository
) {

    fun create(monetaryConverterDTO: MonetaryConverterDTO): MonetaryConverterDTO {

        val turnsTypeOutPut = object : TypeToken<MonetaryConverterDTO>() {}.type
        val exchangeRatesApiResponse = exchangeRatesApiService.getRates();
        var monetaryConverter = converterCurrency(monetaryConverterDTO, exchangeRatesApiResponse)
        return ModelMapper().map(monetaryConverterRepository.save(monetaryConverter), turnsTypeOutPut)
    }

    private fun converterCurrency(
        monetaryConverterDTO: MonetaryConverterDTO,
        exchangeRatesApiResponse: ExchangeRatesApiResponse?
    ):MonetaryConverter {
        val monetaryConverter = MonetaryConverter().fillMonetaryConverter(monetaryConverterDTO);

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
        return monetaryConverter;

    }

    fun listAll(): List<MonetaryConverterDTO> {
        val turnsType = object : TypeToken<List<MonetaryConverterDTO>>() {}.type
        return ModelMapper().map(monetaryConverterRepository.findAll(), turnsType);

    }

    fun findforidUsurious(idUsurious:String?): List<MonetaryConverterDTO>{


        IntegerValidation.validate(idUsurious,"idUsurious")

        val turnsType = object : TypeToken<List<MonetaryConverterDTO>>() {}.type

        val listMonetaryConverter = monetaryConverterRepository.findforidUsurious(Integer.valueOf(idUsurious))
        if(listMonetaryConverter.isEmpty()){
            throw NotFoundException("Não foi encontrado MonetaryConverter para o usuário. ");
        }
        return ModelMapper().map(listMonetaryConverter, turnsType);
    }



}