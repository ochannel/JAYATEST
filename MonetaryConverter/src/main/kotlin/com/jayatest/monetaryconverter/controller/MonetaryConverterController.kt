package com.jayatest.monetaryconverter.controller

import com.jayatest.monetaryconverter.model.MonetaryConverter
import com.jayatest.monetaryconverter.repository.MonetaryConverterRepository
import com.jayatest.monetaryconverter.service.MonetaryConverterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/monetaryconverter")
class MonetaryConverterController(val monetaryConverterService: MonetaryConverterService) {

    @GetMapping
    fun listAll()=ResponseEntity.ok().body(monetaryConverterService.listAll())

    @PostMapping
    fun create(@RequestBody monetaryConverter:MonetaryConverter) = ResponseEntity.status(HttpStatus.CREATED).body(monetaryConverterService.create(monetaryConverter))



}