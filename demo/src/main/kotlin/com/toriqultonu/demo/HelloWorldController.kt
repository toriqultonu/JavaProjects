package com.toriqultonu.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class HelloWorldController {

    @GetMapping("tonu")
    fun helloWorld():String {
        return "Hello, this is a Rest endpoint!"
    }
}