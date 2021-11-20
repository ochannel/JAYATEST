package com.jayatest.monetaryconverter.repository

import com.jayatest.monetaryconverter.model.MonetaryConverter
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository


interface MonetaryConverterRepository : MongoRepository<MonetaryConverter,String>{

}