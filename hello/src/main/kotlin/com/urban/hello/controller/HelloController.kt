package com.urban.hello.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping
    fun getGreetings(): ResponseEntity<Greetings> {
        return ResponseEntity<Greetings>(Greetings("Hello"), HttpStatus.OK)
    }
}

data class Greetings(
    val greetings: String
)
