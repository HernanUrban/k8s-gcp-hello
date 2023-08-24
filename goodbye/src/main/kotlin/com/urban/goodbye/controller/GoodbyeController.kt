package com.urban.goodbye.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GoodbyController {

    @GetMapping
    fun getSalute(): ResponseEntity<Greetings> {
        return ResponseEntity<Greetings>(Greetings("Goodbye"), HttpStatus.OK)
    }
}

data class Greetings(
    val greetings: String
)