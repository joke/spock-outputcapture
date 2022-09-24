package io.github.joke.test;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class ErrorLogger {

    private static final Logger log = getLogger(ErrorLogger.class);

    ErrorLogger(String name) {
        log.info("Logger with name {} created", name);
    }

    void logSomething(String message) {
        log.error(message);
    }

}
