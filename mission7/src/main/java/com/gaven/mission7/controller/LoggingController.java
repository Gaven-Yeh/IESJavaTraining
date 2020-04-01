package com.gaven.mission7.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @RequestMapping("/")
    public String index() {
        logger.info("INFO");
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.error("ERROR");
        logger.warn("WARN");

        return "Henlo! Check out the Logs to see the output...";
    }

}
