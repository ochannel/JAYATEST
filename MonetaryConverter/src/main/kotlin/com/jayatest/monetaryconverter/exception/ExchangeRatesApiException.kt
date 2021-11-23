package com.jayatest.monetaryconverter.exception

class ExchangeRatesApiException(override var message: String?): RuntimeException(message) {
}