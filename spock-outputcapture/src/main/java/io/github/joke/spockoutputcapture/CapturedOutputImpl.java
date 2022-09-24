package io.github.joke.spockoutputcapture;

import groovy.transform.CompileStatic;
import groovy.transform.PackageScope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;

class CapturedOutputImpl implements CapturedOutput {

    private final StringBuffer buffer = new StringBuffer();

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public List<String> getLines() {
        return asList(buffer.toString().split("(\\r\\n|\\n|\\r)"));
    }

    synchronized StringBuffer append(String str) {
        return buffer.append(str);
    }

}
