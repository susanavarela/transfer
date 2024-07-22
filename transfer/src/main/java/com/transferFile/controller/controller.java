package com.transferFile.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class controller {


    public static final String PROCESS = "/process";

    @PostMapping(PROCESS)
    public ResponseEntity<Object> processInput(@RequestBody String input) throws InterruptedException {

        return ResponseEntity.ok("OK");
    }


}
