package com.gaven.helloworld;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloWorldController {

    @RequestMapping(value="/home")
    @ResponseBody
    public String sayHello(){
        return "Hello World";
    }
}
