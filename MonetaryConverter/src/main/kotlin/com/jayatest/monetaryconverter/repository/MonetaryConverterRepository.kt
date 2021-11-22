package com.jayatest.monetaryconverter.repository

import com.jayatest.monetaryconverter.model.MonetaryConverter
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


interface MonetaryConverterRepository : MongoRepository<MonetaryConverter,String>{




    @Query("{ 'idUsurious' : ?0 }")
    fun findforidUsurious(idUsurious:Int?):List<MonetaryConverter>

}