package com.david.practica.practicaspring.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/api/holamundo/{message}")
    public String loginMessage(@PathVariable String message){
        return "Hola " + message;
    }
}

