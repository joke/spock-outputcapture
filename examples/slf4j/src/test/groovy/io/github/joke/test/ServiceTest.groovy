package io.github.joke.test

import io.github.joke.spockoutputcapture.OutputCapture
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class ServiceTest extends Specification {

    @Shared
    ErrorLogger globalLogger = new ErrorLogger('globalLogger')
    ErrorLogger localLogger = new ErrorLogger('localLogger')

    @OutputCapture @Shared globalLogs
    @OutputCapture localLogs

    def 'local logging service: Hello World'() {
        setup:
        localLogger.logSomething('Hello World')

        expect:
        localLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger created.*/
        localLogs ==~ /(?s).*Hello World.*/
        !(localLogs ==~ /(?s).*Hello Test.*/)
    }

    def 'local logging service: Hello Test'() {
        setup:
        localLogger.logSomething('Hello Test')

        expect:
        localLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger created.*/
        localLogs ==~ /(?s).*Hello Test.*/
        !(localLogs ==~ /(?s).*Hello World.*/)
    }

    def 'global logging service'() {
        setup:
        globalLogger.logSomething('Hello Universe')

        expect:
        globalLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name globalLogger created.*/
        globalLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger created.*/
        globalLogs ==~ /(?s).*Hello Test.*/
        globalLogs ==~ /(?s).*Hello World.*/
        globalLogs ==~ /(?s).*Hello Universe.*/
        localLogs ==~ /(?s).*Hello Universe.*/
    }
}
