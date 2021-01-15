package io.github.joke.test

import io.github.joke.spockoutputcapture.OutputCapture
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
@SpringBootTest
class ApplicationTest extends Specification {

    @Shared
    @OutputCapture
    globalLogs

    @OutputCapture
    localLogs

    @Autowired
    LoggingService loggingService

    def 'global messages during startup are captured'() {
        expect:
        globalLogs ==~ /(?s).*:: Spring Boot ::.*/
        globalLogs ==~ /(?s).*Started ApplicationTest in.*/

        !(localLogs ==~ 'My name is LoggingService.')
    }

    def 'local messages are captured'() {
        setup:
        loggingService.logYourName()

        expect:
        localLogs ==~ /(?s).*My name is LoggingService.*/
        !(localLogs ==~ /(?s).*:: Spring Boot ::.*/)
        !(localLogs ==~ /(?s).*Started ApplicationTest in.*/)

        globalLogs ==~ /(?s).*:: Spring Boot ::.*/
        globalLogs ==~ /(?s).*Started ApplicationTest in.*/
        globalLogs ==~ /(?s).*My name is LoggingService.*/
    }
}
