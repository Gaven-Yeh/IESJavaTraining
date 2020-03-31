package com.gaven.mission6.controller;

import com.gaven.mission6.config.PersonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;import org.springframework.web.bind.annotation.RestController;

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
