package io.github.joke.test

import io.github.joke.spockoutputcapture.CapturedOutput
import io.github.joke.spockoutputcapture.OutputCapture
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class TypedLogs extends Specification {

    @Shared
    ErrorLogger globalLogger = new ErrorLogger('globalLogger')
    ErrorLogger localLogger = new ErrorLogger('localLogger')

    @Shared @OutputCapture CapturedOutput globalLogs
    @OutputCapture CapturedOutput localLogs

    def 'local logging service: Hello World'() {
        setup:
        localLogger.logSomething('Hello World')

        expect:
        localLogs.lines
        verifyAll(localLogs.lines) {
            size() == 2
            it.any { it ==~ /.*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger created.*/ }
            it.any { it.contains 'Hello World' }
            !it.any { it.contains 'globalLogger' }
        }
    }

    def 'local logging service: Hello Test'() {
        setup:
        localLogger.logSomething('Hello Test')

        expect:
        verifyAll(localLogs.lines) {
            size() == 2
            it.any { it.contains 'Hello Test' }
            it.any { it ==~ /.*Hello Test.*/ }
        }
    }

    def 'global logging should contain all logs'() {
        expect:
        verifyAll(globalLogs.lines) {
            size() == 6
            it.any { it.contains 'Hello Test' }
            it.any { it.contains 'globalLogger' }
            it.any { it.contains 'localLogger' }
        }
    }

    def 'global logging service'() {
        setup:
        globalLogger.logSomething('Hello Universe')

        expect:
        globalLogs.lines ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name globalLogger created.*/
        globalLogs.lines ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger created.*/
        globalLogs.lines ==~ /(?s).*Hello Test.*/
        globalLogs.lines ==~ /(?s).*Hello World.*/
        globalLogs.lines ==~ /(?s).*Hello Universe.*/
        localLogs.lines==~ /(?s).*Hello Universe.*/
    }

    def 'clear logger'() {
        when:
        localLogger.logSomething('Hello Test')

        then:
        localLogs ==~ /(?s).*Hello Test.*/

        and:
        when:
        localLogs.clear()
        localLogger.logSomething('Hello again Test')

        then:
        !(localLogs ==~ /(?s).*Hello Test.*/)
        localLogs ==~ /(?s).*Hello again Test.*/
    }
}
