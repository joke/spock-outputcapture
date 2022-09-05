package io.github.joke.test;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class LoggingService {
    private static final Logger log = getLogger(LoggingService.class);

    public void logYourName() {
        log.info("My name is {}.", LoggingService.class.getSimpleName());
    }

}
