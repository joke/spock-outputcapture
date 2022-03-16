package io.github.joke.test

import groovy.util.logging.Slf4j

@Slf4j
class ErrorLogger {

    ErrorLogger(String name) {
        log.info("Logger with name {} created", name)
    }

    void logSomething(String message) {
        log.error(message)
    }

}
