package com.jayatest.monetaryconverter.validation

import com.jayatest.monetaryconverter.exception.ValidationException

class IntegerValidation {

    companion object {
        @JvmStatic
        fun validate(number: String?=null, name: String) {
            try {
                Integer.parseInt(number);
            } catch (e: Exception) {
               throw  ValidationException("O campo ${name} não é um número entre 0 e 999999999.")
            }

        }
    }
}