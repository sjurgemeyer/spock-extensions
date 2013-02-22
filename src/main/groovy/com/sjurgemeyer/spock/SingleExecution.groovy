package com.sjurgemeyer.spock

import org.spockframework.runtime.extension.ExtensionAnnotation
import java.lang.annotation.*

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtensionAnnotation(SingleExecutionExtension)
@interface SingleExecution{
    String value()
}
