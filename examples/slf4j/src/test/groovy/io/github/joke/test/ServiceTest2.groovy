package io.github.joke.test

import io.github.joke.spockoutputcapture.OutputCapture
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class ServiceTest2 extends Specification {

    @Shared
    ErrorLogger globalLogger2 = new ErrorLogger('globalLogger2')
    ErrorLogger localLogger2 = new ErrorLogger('localLogger2')

    @OutputCapture @Shared globalLogs
    @OutputCapture localLogs

    def 'local logging service: Hallo World'() {
        setup:
        localLogger2.logSomething('Hallo World')

        expect:
        localLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger2 created.*/
        localLogs ==~ /(?s).*Hallo World.*/
        !(localLogs ==~ /(?s).*Hallo Test.*/)
    }

    def 'local logging service: Hallo Test'() {
        setup:
        localLogger2.logSomething('Hallo Test')

        expect:
        localLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger2 created.*/
        localLogs ==~ /(?s).*Hallo Test.*/
        !(localLogs ==~ /(?s).*Hallo World.*/)
    }

    def 'global logging service'() {
        setup:
        globalLogger2.logSomething('Hallo Universe')

        expect:
        globalLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name globalLogger2 created.*/
        globalLogs ==~ /(?s).*INFO io.github.joke.test.ErrorLogger - Logger with name localLogger2 created.*/
        globalLogs ==~ /(?s).*Hallo Test.*/
        globalLogs ==~ /(?s).*Hallo World.*/
        globalLogs ==~ /(?s).*Hallo Universe.*/
        localLogs ==~ /(?s).*Hallo Universe.*/
    }

}
