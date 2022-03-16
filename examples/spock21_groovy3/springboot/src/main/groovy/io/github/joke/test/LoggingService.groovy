package io.github.joke.test

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class LoggingService {

    def logYourName() {
        log.info("My name is ${this.class.simpleName}.")
    }

}
