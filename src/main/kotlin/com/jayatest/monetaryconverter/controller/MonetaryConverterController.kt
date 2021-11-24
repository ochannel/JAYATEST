package com.jayatest.monetaryconverter.controller

import com.jayatest.monetaryconverter.api.V1Api
import com.jayatest.monetaryconverter.model.MonetaryConverter
import com.jayatest.monetaryconverter.repository.MonetaryConverterRepository
import com.jayatest.monetaryconverter.service.MonetaryConverterService
import com.jayatest.monetaryconverterapi.model.MonetaryConverterDTO
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class MonetaryConverterController(val monetaryConverterService: MonetaryConverterService) : V1Api {

    override fun listAll() = ResponseEntity.ok().body(monetaryConverterService.listAll());

    override fun create(monetaryConverterDTO: MonetaryConverterDTO) =
        ResponseEntity.status(HttpStatus.CREATED).body(monetaryConverterService.create(monetaryConverterDTO))

    override fun findforidUsurious(idUsurious: Int?)
            : ResponseEntity<List<MonetaryConverterDTO>>? =
        ResponseEntity.ok().body(monetaryConverterService.findforidUsurious(idUsurious));


}