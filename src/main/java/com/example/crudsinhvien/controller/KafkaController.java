package com.example.crudsinhvien.controller;

import com.example.crudsinhvien.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducerService producerService;

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        producerService.sendMessage("my-topic", message);
        return "Message sent to Kafka!";
    }
}
