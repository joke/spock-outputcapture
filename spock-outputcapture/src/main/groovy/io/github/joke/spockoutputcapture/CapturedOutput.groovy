package io.github.joke.spockoutputcapture

interface CapturedOutput {

    @Override
    String toString();

    List<String> getLines();

}
