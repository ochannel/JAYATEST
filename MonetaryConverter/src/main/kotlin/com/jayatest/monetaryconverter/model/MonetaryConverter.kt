package com.jayatest.monetaryconverter.model

import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDateTime


//@Document
data class MonetaryConverter(
    val transactionId:String?=null,
    val idUsurious: Long,
    val origenCurrency:Moeda,
    val origenCurrencyValue: BigDecimal,
    val destinationCurrency :Moeda,
    val destinationCurrencyValue :BigDecimal?=null,
    val rate: BigDecimal?=null,
    val currentDate: LocalDateTime?=null
    ) {
}