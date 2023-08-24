package com.urban.goodbye

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GoodbyeApplication

fun main(args: Array<String>) {
	runApplication<GoodbyeApplication>(*args)
}
