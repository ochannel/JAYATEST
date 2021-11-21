package com.jayatest.monetaryconverter.model

import java.time.LocalDate
import java.time.LocalTime

data class ExchangeRatesApiResponse(
    val success: Boolean?=null,
    val timestamp: Long?=null,
    val base: String?=null,
    val date: String?=null,
    val rates: Map<String, Double>?=null
){

}