package io.github.joke.spockoutputcapture;

import groovy.lang.GroovyObject;
import lombok.RequiredArgsConstructor;
import org.spockframework.runtime.IStandardStreamsListener;
import org.spockframework.runtime.InvalidSpecException;
import org.spockframework.runtime.StandardStreamsCapturer;
import org.spockframework.runtime.extension.IAnnotationDrivenExtension;
import org.spockframework.runtime.extension.IMethodInvocation;
import org.spockframework.runtime.model.FieldInfo;
import org.spockframework.runtime.model.SpecInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OutputCaptureExtension implements IAnnotationDrivenExtension<OutputCapture> {

    private final Map<FieldInfo, CapturedOutputImpl> fieldBuffers = new ConcurrentHashMap<>();

    @Override
    public void visitFieldAnnotation(final OutputCapture annotation, final FieldInfo field) {
        if (!field.getReflection().getType().isAssignableFrom(CapturedOutput.class)) {
            throw new InvalidSpecException(
                    "Wrong type for field " + field.getName() + "\n" +
                            "@io.github.joke.spockoutputcapture.OutputCapture can only be placed on fields assignableFrom CapturedOutput." +
                            "Choose either" +
                            "@io.github.joke.spockoutputcapture.OutputCapture logs" +
                            "@io.github.joke.spockoutputcapture.OutputCapture CapturedOutput logs");
        }
        fieldBuffers.put(field, new CapturedOutputImpl());
    }

    @Override
    public void visitSpec(final SpecInfo spec) {
        final StandardStreamsCapturer capturer = new StandardStreamsCapturer();
        capturer.addStandardStreamsListener(new Listener(fieldBuffers));
        spec.addInitializerInterceptor(this::interceptInitializer);
        spec.addSharedInitializerInterceptor(invocation -> interceptSharedInitializer(invocation, capturer));
        spec.addCleanupSpecInterceptor(invocation -> interceptCleanupSpec(invocation, capturer));
    }

    protected void interceptInitializer(final IMethodInvocation invocation) throws Throwable {
        fieldBuffers.keySet().stream()
                .filter(fieldInfo -> !fieldInfo.isShared())
                .forEach(fieldInfo -> setNewBufferToField(fieldInfo, (GroovyObject) invocation.getInstance())
                );
        invocation.proceed();
    }

    protected void interceptSharedInitializer(final IMethodInvocation invocation, final StandardStreamsCapturer capturer) throws Throwable {
        capturer.start();
        fieldBuffers.keySet().stream()
                .filter(FieldInfo::isShared)
                .forEach(fieldInfo -> setNewBufferToField(fieldInfo, (GroovyObject) invocation.getInstance())
        );
        invocation.proceed();
    }

    protected void interceptCleanupSpec(final IMethodInvocation invocation, final StandardStreamsCapturer capturer) throws Throwable {
        capturer.stop();
        invocation.proceed();
    }

    void setNewBufferToField(final FieldInfo fieldInfo, final GroovyObject instance) {
        final CapturedOutputImpl buffer = new CapturedOutputImpl();
        fieldBuffers.put(fieldInfo, buffer);
        final String fieldName = fieldInfo.getReflection().getName();
        instance.getMetaClass().setProperty(instance, fieldName, buffer);
    }

    @RequiredArgsConstructor
    private static class Listener implements IStandardStreamsListener {

        private final Map<FieldInfo, CapturedOutputImpl> fieldBuffers;

        @Override
        public void standardOut(final String message) {
            fieldBuffers.values()
                    .forEach(capturedOutput -> capturedOutput.append(message));
        }

        @Override
        public void standardErr(final String message) {
            fieldBuffers.values()
                    .forEach(capturedOutput -> capturedOutput.append(message));
        }
    }
}
