package com.example.loom.controller;

import com.example.loom.pojo.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/api")
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);

   private final Random r = new Random();


    @GetMapping("/persons")
    public List<Person> getPersons(){


        //   LOGGER.error("In get persons "+Thread.currentThread().isVirtual());
        Person person = new Person("applicationTaskExecutor", "Spring", 17);
        try {
            int low = 500;
            int high = 1200;
            TimeUnit.MILLISECONDS.sleep(r.nextInt(high - low) + low);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return List.of(person);
    }


}
