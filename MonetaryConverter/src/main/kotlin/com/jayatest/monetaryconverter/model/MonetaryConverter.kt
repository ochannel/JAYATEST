package com.jayatest.monetaryconverter.model

import org.springframework.data.annotation.Id
import java.math.BigDecimal
import java.time.LocalDateTime


//@Document
data class MonetaryConverter(
    @Id
    val transactionId:String?=null,
    val idUsurious: Long,
    val origenCurrency:Currency,
    var origenCurrencyValue: BigDecimal,
    val destinationCurrency :Currency,
    var destinationCurrencyValue :BigDecimal?=null,
    var rate: BigDecimal?=null,
    var currentDate: LocalDateTime?=null
    ) {
}