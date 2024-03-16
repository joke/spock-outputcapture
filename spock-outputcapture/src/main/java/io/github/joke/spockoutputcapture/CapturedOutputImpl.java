package io.github.joke.spockoutputcapture;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class CapturedOutputImpl implements CapturedOutput {

    private static final Pattern NEWLINES = Pattern.compile("(\\r\\n|\\n|\\r)");

    private final StringBuffer buffer = new StringBuffer();

    @Override
    public String toString() {
        return buffer.toString();
    }

    @Override
    public List<String> getLines() {
        if (buffer.length() == 0) {
            return emptyList();
        }
        return NEWLINES.splitAsStream(buffer.toString())
                .collect(toList());
    }

    @Override
    public void clear() {
        buffer.setLength(0);
    }

    synchronized StringBuffer append(String str) {
        return buffer.append(str);
    }

}
