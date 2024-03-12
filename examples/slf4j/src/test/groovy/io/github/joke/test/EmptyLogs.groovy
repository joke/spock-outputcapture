package io.github.joke.test

import io.github.joke.spockoutputcapture.CapturedOutput
import io.github.joke.spockoutputcapture.OutputCapture
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
class EmptyLogs extends Specification {

    @Shared @OutputCapture CapturedOutput globalLogs
    @OutputCapture CapturedOutput localLogs

    def 'empty logs'() {
        expect:
        globalLogs.lines.empty
        localLogs.lines.empty
    }
}
