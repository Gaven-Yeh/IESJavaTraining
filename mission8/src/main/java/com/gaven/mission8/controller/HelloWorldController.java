package com.gaven.mission8.controller;

import com.gaven.mission8.config.PersonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class HelloWorldController {

    @Autowired
    PersonProperties personProperties;

    @Value("${message: Hello default}")
    private String message;

    @RequestMapping(value = "/home")
    @ResponseBody
    public String sayHello() {
        return message + personProperties.getType() + " " + personProperties.getName() + "!";
    }
}
