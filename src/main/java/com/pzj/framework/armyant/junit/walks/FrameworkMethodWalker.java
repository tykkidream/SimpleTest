package com.pzj.framework.armyant.junit.walks;

import com.pzj.framework.armyant.junit.RunnerRun;
import com.pzj.framework.armyant.junit.Walker;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.*;
import org.junit.rules.MethodRule;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Saber on 2017/3/18.
 */
public class FrameworkMethodWalker extends Walker {
    private Description description;

    private FrameworkMethod frameworkMethod;

    private TestClass testClass;

    private Statement statement;

    public TestClass getTestClass(){
        return testClass;
    }

    public FrameworkMethodWalker(TestClass testClass, FrameworkMethod frameworkMethod){
        this.testClass = testClass;
        this.frameworkMethod = frameworkMethod;
    }

    @Override
    public Description getDescription() {
        if (description == null){
            description = Description.createTestDescription(testClass.getJavaClass(),
                    frameworkMethod.getName(), frameworkMethod.getAnnotations());
        }
        return description;
    }

    @Override
    public void walk(RunnerRun runnerRun, RunNotifier notifier) {
        runnerRun.run(getStatement(), getDescription(), notifier);
    }


    public Statement getStatement() {
        if (statement == null){
            statement = methodBlock(this.frameworkMethod);
        }
        return statement;
    }



    private Statement methodBlock(FrameworkMethod method) {
        Object test;
        try {
            test = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();
        } catch (Throwable e) {
            return new Fail(e);
        }

        Statement statement = methodInvoker(method, test);
        statement = possiblyExpectingExceptions(method, test, statement);
        statement = withPotentialTimeout(method, test, statement);
        statement = withBefores(method, test, statement);
        statement = withAfters(method, test, statement);
        statement = withRules(method, test, statement);
        return statement;
    }

    protected Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance();
    }

    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new InvokeMethod(method, test);
    }

    protected Statement possiblyExpectingExceptions(FrameworkMethod method,
                                                    Object test, Statement next) {
        Test annotation = method.getAnnotation(Test.class);
        return expectsException(annotation) ? new ExpectException(next,
                getExpectedException(annotation)) : next;
    }

    @Deprecated
    protected Statement withPotentialTimeout(FrameworkMethod method,
                                             Object test, Statement next) {
        long timeout = getTimeout(method.getAnnotation(Test.class));
        if (timeout <= 0) {
            return next;
        }
        return FailOnTimeout.builder()
                .withTimeout(timeout, TimeUnit.MILLISECONDS)
                .build(next);
    }

    protected Statement withBefores(FrameworkMethod method, Object target,
                                    Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(
                Before.class);
        return befores.isEmpty() ? statement : new RunBefores(statement,
                befores, target);
    }

    protected Statement withAfters(FrameworkMethod method, Object target,
                                   Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(
                After.class);
        return afters.isEmpty() ? statement : new RunAfters(statement, afters,
                target);
    }

    private Statement withRules(FrameworkMethod method, Object target,
                                Statement statement) {
        List<TestRule> testRules = getTestRules(target);
        Statement result = statement;
        result = withMethodRules(method, testRules, target, result);
        result = withTestRules(method, testRules, result);

        return result;
    }

    private Statement withMethodRules(FrameworkMethod method, List<TestRule> testRules,
                                      Object target, Statement result) {
        for (org.junit.rules.MethodRule each : getMethodRules(target)) {
            if (!testRules.contains(each)) {
                result = each.apply(result, method, target);
            }
        }
        return result;
    }
    private Statement withTestRules(FrameworkMethod method, List<TestRule> testRules,
                                    Statement statement) {
        return testRules.isEmpty() ? statement :
                new RunRules(statement, testRules, getDescription());
    }


    private List<org.junit.rules.MethodRule> getMethodRules(Object target) {
        return rules(target);
    }
    protected List<MethodRule> rules(Object target) {
        List<MethodRule> rules = getTestClass().getAnnotatedMethodValues(target,
                Rule.class, MethodRule.class);

        rules.addAll(getTestClass().getAnnotatedFieldValues(target,
                Rule.class, MethodRule.class));

        return rules;
    }

    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> result = getTestClass().getAnnotatedMethodValues(target,
                Rule.class, TestRule.class);

        result.addAll(getTestClass().getAnnotatedFieldValues(target,
                Rule.class, TestRule.class));

        return result;
    }


    private Class<? extends Throwable> getExpectedException(Test annotation) {
        if (annotation == null || annotation.expected() == Test.None.class) {
            return null;
        } else {
            return annotation.expected();
        }
    }

    private boolean expectsException(Test annotation) {
        return getExpectedException(annotation) != null;
    }

    private long getTimeout(Test annotation) {
        if (annotation == null) {
            return 0;
        }
        return annotation.timeout();
    }
}
