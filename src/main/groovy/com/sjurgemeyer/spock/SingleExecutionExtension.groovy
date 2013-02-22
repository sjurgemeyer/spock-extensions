package com.sjurgemeyer.spock

import groovy.transform.InheritConstructors
import org.spockframework.runtime.extension.*
import org.spockframework.runtime.model.*

class SingleExecutionExtension extends AbstractAnnotationDrivenExtension<SingleExecution> {

	@Override
	void visitFeatureAnnotation(SingleExecution annotation, FeatureInfo feature) {
		def interceptor = new SingleExecutionInterceptor(annotation.value(), feature)
        feature.addIterationInterceptor interceptor
	}
}

class SingleExecutionInterceptor extends AbstractMethodInterceptor {

    boolean called = false
    String closure
    Object result
    FeatureInfo feature

    SingleExecutionInterceptor(String closure, FeatureInfo feature) {
        this.closure = closure
        this.feature = feature
    }

    @Override
    void interceptFeatureMethod(IMethodInvocation invocation) throws Throwable {
        invocation.proceed()
    }

    @Override
    public void interceptIterationExecution(IMethodInvocation invocation) throws Throwable {
        if (!called) {
            result = invocation.instance."$closure".call()
            called = true
        }
        invocation.instance.metaClass.getResult = { -> result }
        invocation.proceed()
    }
}
