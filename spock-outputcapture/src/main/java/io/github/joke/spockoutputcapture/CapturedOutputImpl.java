package io.github.joke.spockoutputcapture;

import java.util.List;

import static java.util.Arrays.asList;

public class CapturedOutputImpl implements CapturedOutput {

    private final StringBuffer buffer = new StringBuffer();

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public List<String> getLines() {
        return asList(buffer.toString().split("(\\r\\n|\\n|\\r)"));
    }

    @Override
    public void clear() {
        buffer.setLength(0);
    }

    synchronized StringBuffer append(String str) {
        return buffer.append(str);
    }

}
