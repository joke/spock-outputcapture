package io.github.joke.spockoutputcapture;

import java.util.List;

public interface CapturedOutput {

    @Override
    String toString();

    List<String> getLines();

    void clear();

}
