package com.pzj.framework.armyant.junit.runtask;

import com.pzj.framework.armyant.junit.RunTask;
import org.junit.*;
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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.internal.runners.rules.RuleMemberValidator.RULE_METHOD_VALIDATOR;
import static org.junit.internal.runners.rules.RuleMemberValidator.RULE_VALIDATOR;

/**
 * Created by Saber on 2017/3/18.
 */
public class SimpleRunTask extends AbstractTask implements RunTask {
    protected FrameworkMethod frameworkMethod;

    public SimpleRunTask(TestClass testClass, FrameworkMethod frameworkMethod){
        super(testClass);
        this.frameworkMethod = frameworkMethod;
    }

    public FrameworkMethod getFrameworkMethod() {
        return frameworkMethod;
    }

    @Override
    public Description getDescription() {
        if (description == null){
            description = Description.createTestDescription(testClass.getJavaClass(),
                    frameworkMethod.getName(), frameworkMethod.getAnnotations());
        }
        return description;
    }

    public Statement createStatement(Object target) {
        Statement statement = methodInvoker(target);
        statement = possiblyExpectingExceptions(target, statement);
        statement = withPotentialTimeout(target, statement);
        statement = withBefores(target, statement);
        statement = withAfters(target, statement);
        statement = withRules(target, statement);
        return statement;
    }

    @Override
    public boolean isIgnored() {
        return getFrameworkMethod().getAnnotation(Ignore.class) != null;
    }

    protected Statement methodInvoker(Object target) {
        return new InvokeMethod(getFrameworkMethod(), target);
    }

    protected Statement possiblyExpectingExceptions(Object target, Statement next) {
        Test annotation = getFrameworkMethod().getAnnotation(Test.class);
        return expectsException(annotation) ? new ExpectException(next, getExpectedException(annotation)) : next;
    }

    @Deprecated
    protected Statement withPotentialTimeout(Object target, Statement next) {
        long timeout = getTimeout(getFrameworkMethod().getAnnotation(Test.class));
        if (timeout <= 0) {
            return next;
        }
        return FailOnTimeout.builder().withTimeout(timeout, TimeUnit.MILLISECONDS).build(next);
    }

    protected Statement withBefores(Object target, Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(Before.class);
        return befores.isEmpty() ? statement : new RunBefores(statement, befores, target);
    }

    protected Statement withAfters(Object target, Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(After.class);
        return afters.isEmpty() ? statement : new RunAfters(statement, afters, target);
    }

    private Statement withRules(Object target, Statement statement) {
        List<TestRule> testRules = getTestRules(target);
        Statement result = statement;
        result = withMethodRules(testRules, target, result);
        result = withTestRules(testRules, result);
        return result;
    }

    private Statement withMethodRules(List<TestRule> testRules, Object target, Statement result) {
        for (org.junit.rules.MethodRule each : getMethodRules(target)) {
            if (!testRules.contains(each)) {
                result = each.apply(result, getFrameworkMethod(), target);
            }
        }
        return result;
    }

    private List<org.junit.rules.MethodRule> getMethodRules(Object target) {
        List<MethodRule> rules = getTestClass().getAnnotatedMethodValues(target, Rule.class, MethodRule.class);
        rules.addAll(getTestClass().getAnnotatedFieldValues(target, Rule.class, MethodRule.class));
        return rules;
    }

    private Statement withTestRules(List<TestRule> testRules, Statement statement) {
        return testRules.isEmpty() ? statement : new RunRules(statement, testRules, getDescription());
    }

    protected List<TestRule> getTestRules(Object target) {
        List<TestRule> result = getTestClass().getAnnotatedMethodValues(target, Rule.class, TestRule.class);

        result.addAll(getTestClass().getAnnotatedFieldValues(target, Rule.class, TestRule.class));
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
