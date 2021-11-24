package com.jayatest.monetaryconverter.model

import java.time.LocalDate
import java.time.LocalTime

data class ExchangeRatesApiResponse(
    var success: Boolean?=null,
    var timestamp: Long?=null,
    var base: String?=null,
    var date: String?=null,
    var rates: Map<String, Double>?=null
){

}