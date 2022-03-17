package io.github.joke.spockoutputcapture

import groovy.transform.CompileStatic
import groovy.transform.PackageScope

@PackageScope
@CompileStatic
class CapturedOutputImpl implements CapturedOutput {

    @Delegate
    private StringBuffer buffer = new StringBuffer()

    @Override
    String toString() {
        buffer.toString()
    }

    @Override
    List<String> getLines() {
        buffer.toString().split('(\r\n|\n|\r)') as List<String>
    }

}
