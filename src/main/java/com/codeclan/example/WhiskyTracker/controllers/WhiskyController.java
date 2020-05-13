package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.models.Whisky;
import org.springframework.beans.factory.annotation.Autowired;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/whiskies")
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;

    @GetMapping
    public ResponseEntity<List<Whisky>> getAllWhiskies(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "distillery", required = false) Distillery distillery,
            @RequestParam(name = "region", required = false) String region,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "starting", required = false) String starting
            )
    {
        if(year != null){
           return new ResponseEntity<>(whiskyRepository.findByYear(year), HttpStatus.OK);
        }

        if(region != null){
            return new ResponseEntity<>(whiskyRepository.findByDistilleryRegion(region), HttpStatus.OK);
        }

        if(name != null){
            return new ResponseEntity<>(whiskyRepository.findByNameContaining(name), HttpStatus.OK);
        }

        if(starting != null){
            return new ResponseEntity<>(whiskyRepository.findByNameStartingWith(starting), HttpStatus.OK);
        }

        if(age != null && distillery != null){
            return new ResponseEntity<>(whiskyRepository.findByDistilleryAndAge(distillery, age), HttpStatus.OK);
        }

        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);

    }

}
