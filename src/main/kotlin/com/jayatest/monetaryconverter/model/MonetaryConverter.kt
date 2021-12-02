package com.jayatest.monetaryconverter.model

import com.jayatest.monetaryconverterapi.model.MonetaryConverterDTO
import com.jayatest.monetaryconverterapi.model.MonetaryConverterRequestDTO
import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime


//@Document
data class MonetaryConverter(
    @Id
    var transactionId:String?=null,
    var idUser: Long?=null,
    var origenCurrency:Currency?=null,
    var origenCurrencyValue: BigDecimal?=null,
    var destinationCurrency :Currency?=null,
    var destinationCurrencyValue :BigDecimal?=null,
    var rate: BigDecimal?=null,
    var currentDate: LocalDateTime?=null
    ) {


    public fun fillMonetaryConverter(monetaryConverterDTO: MonetaryConverterRequestDTO):MonetaryConverter{
        this.idUser = monetaryConverterDTO.idUser.toLong();
        this.origenCurrency= Currency.valueOf(monetaryConverterDTO.origenCurrency);
        this.origenCurrencyValue=BigDecimal(monetaryConverterDTO.origenCurrencyValue);
        this.destinationCurrency=Currency.valueOf(monetaryConverterDTO.destinationCurrency);
         return this;
    }





}