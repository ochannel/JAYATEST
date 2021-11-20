package com.jayatest.monetaryconverter.service

import com.jayatest.monetaryconverter.model.MonetaryConverter
import com.jayatest.monetaryconverter.repository.MonetaryConverterRepository
import org.springframework.stereotype.Service

@Service
class MonetaryConverterService ( val monetaryConverterRepository: MonetaryConverterRepository){


    fun create(monetaryConverter: MonetaryConverter):MonetaryConverter{
       return monetaryConverterRepository.save(monetaryConverter)
    }
}