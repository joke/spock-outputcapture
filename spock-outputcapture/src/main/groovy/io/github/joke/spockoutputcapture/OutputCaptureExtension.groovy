package io.github.joke.spockoutputcapture

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.spockframework.runtime.IStandardStreamsListener
import org.spockframework.runtime.InvalidSpecException
import org.spockframework.runtime.StandardStreamsCapturer
import org.spockframework.runtime.extension.IAnnotationDrivenExtension
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FieldInfo
import org.spockframework.runtime.model.SpecInfo

@CompileStatic
class OutputCaptureExtension implements IAnnotationDrivenExtension<OutputCapture> {

    private Map<FieldInfo, CapturedOutputImpl> fieldBuffers = [:]

    @Override
    void visitFieldAnnotation(OutputCapture annotation, FieldInfo field) {
        if (!field.reflection.type.isAssignableFrom(CapturedOutput)) {
            throw new InvalidSpecException("""Wrong type for field %s.
                |@OutputCapture can only be placed on fields assignableFrom CapturedOutput.
                |Choose either
                |@OutputCapture logs
                |@OutputCapture CapturedOutput logs
                |""".stripMargin()).withArgs(field.name)
        }
        fieldBuffers[(field)] = new CapturedOutputImpl()
    }

    @Override
    void visitSpec(SpecInfo spec) {
        def capturer = new StandardStreamsCapturer()
        capturer.addStandardStreamsListener(new Listener(fieldBuffers))

        spec.addSharedInitializerInterceptor { IMethodInvocation invocation ->
            capturer.start()

            fieldBuffers.keySet().each {
                if (it.shared)
                    setNewBufferToField(it, invocation.instance)
            }
            invocation.proceed()
        }

        spec.addInitializerInterceptor { IMethodInvocation invocation ->
            fieldBuffers.keySet().each {
                if (!it.shared)
                    setNewBufferToField(it, invocation.instance)
            }
            invocation.proceed()
        }

        spec.addCleanupSpecInterceptor {
            capturer.stop()
            it.proceed()
        }
    }

    void setNewBufferToField(FieldInfo fieldInfo, Object instance) {
        def buffer = new CapturedOutputImpl()
        fieldBuffers[(fieldInfo)] = buffer
        instance.metaClass.setProperty(instance, fieldInfo.reflection.name, buffer)
    }

    @TupleConstructor(includeFields = true)
    static class Listener implements IStandardStreamsListener {

        private Map<FieldInfo, CapturedOutputImpl> fieldBuffers

        @Override
        void standardOut(String message) {
            fieldBuffers.values().each { it.append message }
        }

        @Override
        void standardErr(String message) {
            fieldBuffers.values().each { it.append message }
        }
    }
}
