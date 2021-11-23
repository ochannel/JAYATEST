package com.jayatest.monetaryconverter.exception

import com.jayatest.monetaryconverter.model.ApiError
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class ErrorHandlingControllerAdvice : Logging {


	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(
		MethodArgumentNotValidException::class
	)
	fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ApiError? {
		logger.info(String.format("excecao capturada: %s - %s", e.javaClass, e.message))
		val fieldError = e.bindingResult.fieldError
		val mensagem = String.format("%s %s", fieldError!!.field, fieldError.defaultMessage)
		return ApiError(mensagem)
	}

	@ExceptionHandler(HttpMessageNotReadableException::class)
	fun handleErrorHttpMessageNotReadableException(req: HttpServletRequest?, ex: Exception): ResponseEntity<ApiError> {
		logger.info(String.format("excecao capturada: %s - %s", ex.javaClass, ex.message))
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError(ex.message))
	}

	@ExceptionHandler(NotFoundException::class)
	fun handleErrorNotFoundException(req: HttpServletRequest?, ex: Exception): ResponseEntity<ApiError> {
		logger.info(String.format("excecao capturada: %s - %s", ex.javaClass, ex.message))
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError(ex.message))
	}

	@ExceptionHandler(ValidationException::class)
	fun handleErrorValidationException(req: HttpServletRequest?, ex: Exception): ResponseEntity<ApiError> {
		logger.info(String.format("excecao capturada: %s - %s", ex.javaClass, ex.message))
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError(ex.message))
	}

	@ExceptionHandler(ExchangeRatesApiException::class)
	fun handleErrorExchangeRatesApiException(req: HttpServletRequest?, ex: Exception): ResponseEntity<ApiError> {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiError("Internal Server Error"))
	}



}