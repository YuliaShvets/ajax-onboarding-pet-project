package ua.lviv.iot.parkingServer.controller.exceptionHandler

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ua.lviv.iot.parkingServer.logic.exception.EntityNotFoundException
import ua.lviv.iot.parkingServer.logic.exception.message.ErrorMessageModel

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleEntityNotFoundException(ex: EntityNotFoundException): ResponseEntity<ErrorMessageModel> {
        val errorMessage = ErrorMessageModel(
            HttpStatus.NOT_FOUND.value(),
            ex.message ?: ""
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}