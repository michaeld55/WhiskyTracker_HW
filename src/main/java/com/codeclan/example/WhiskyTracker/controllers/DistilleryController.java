package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/distilleries")
public class DistilleryController {

    @Autowired
    DistilleryRepository distilleryRepository;

    @GetMapping
    public ResponseEntity<List<Distillery>> getAllDistilleries(
            @RequestParam(name = "region", required = false)String region,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "starting", required = false) String starting
    )
    {
        if(region != null){
           return new ResponseEntity<>(distilleryRepository.findByRegion(region), HttpStatus.OK);
        }

        if(age != null){
            return new ResponseEntity<>(distilleryRepository.findByWhiskiesAge(age), HttpStatus.OK);
        }

        if(starting != null){
            return new ResponseEntity<>(distilleryRepository.findByWhiskiesNameStartingWith(starting), HttpStatus.OK);
        }

        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Distillery> postDistillery(@RequestBody Distillery distillery){
        distilleryRepository.save(distillery);
        return new ResponseEntity<>(distillery, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Distillery> putDistillery(@RequestBody Distillery distillery,@PathVariable Long id){
        if(!distillery.getId().equals(id) ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        distilleryRepository.save(distillery);
        return new ResponseEntity<>(distillery, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<List<Distillery>> deleteDistillery(@PathVariable Long id){
        distilleryRepository.deleteById(id);
        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }
}
