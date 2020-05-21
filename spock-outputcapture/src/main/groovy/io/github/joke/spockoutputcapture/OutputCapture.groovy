package io.github.joke.spockoutputcapture


import org.spockframework.runtime.extension.ExtensionAnnotation

import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.FIELD
import static java.lang.annotation.RetentionPolicy.RUNTIME

@Target(FIELD)
@Retention(RUNTIME)
@ExtensionAnnotation(OutputCaptureExtension)
@interface OutputCapture {
}
