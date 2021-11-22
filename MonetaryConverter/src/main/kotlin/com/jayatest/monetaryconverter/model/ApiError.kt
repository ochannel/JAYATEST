package com.jayatest.monetaryconverter.model

class ApiError(val message: String?) {

    override fun toString(): String {
        return "ApiError(message=$message)"
    }
}