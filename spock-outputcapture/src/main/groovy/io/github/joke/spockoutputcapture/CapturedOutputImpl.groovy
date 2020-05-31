package io.github.joke.spockoutputcapture

import groovy.transform.PackageScope

@PackageScope
class CapturedOutputImpl implements CapturedOutput {

    @Delegate
    private StringBuffer buffer = new StringBuffer()

    @Override
    String toString() {
        buffer.toString()
    }

    @Override
    List<String> getLines() {
        buffer.toString().split('\n')
    }

}
