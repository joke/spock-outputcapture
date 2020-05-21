package io.github.joke.spockoutputcapture

class CapturedOutput {

    private StringBuffer buffer = new StringBuffer()

    @Override
    String toString() {
        buffer.toString()
    }

    List<String> getLines() {
        buffer.toList()
    }

    protected append(String line) {
        buffer.append(line)
    }

}
