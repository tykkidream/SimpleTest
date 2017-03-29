package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.load.Basket;
import org.junit.*;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.*;
import org.junit.rules.MethodRule;
import org.junit.rules.RunRules;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.internal.runners.rules.RuleMemberValidator.RULE_METHOD_VALIDATOR;
import static org.junit.internal.runners.rules.RuleMemberValidator.RULE_VALIDATOR;

/**
 * Created by Administrator on 2017-1-4.
 */
public class ArmyantRunner extends ParentRunner<RunnerTask> {
    private static Logger logger = LoggerFactory.getLogger(ArmyantRunner.class);

    private List<RunnerTask> runnerTasks;

    public ArmyantRunner(Class<?> clazz) throws Throwable {
        super(clazz);
    }

    private List<RunnerTask> createWalker() {
        List<FrameworkMethod> testMethods = getTestClass().getAnnotatedMethods(Test.class);
        List<FrameworkMethod> testWalkers = getTestClass().getAnnotatedMethods(DataFile.class);

        List<RunnerTask> runnerTasks = new ArrayList<>(testMethods.size());
        for (FrameworkMethod method : testMethods) {
            if (testWalkers.contains(method)) {
                addBatchRunnerTask(runnerTasks, method);
            } else {
                RunnerTask runnerTask = new SimpleRunnerTask(getTestClass(), method);
                runnerTasks.add(runnerTask);
            }
        }

        return runnerTasks;
    }

    private void addBatchRunnerTask(List<RunnerTask> runnerTasks, FrameworkMethod method) {
        List<Basket> baskets = baskets(method);

        if (baskets == null || baskets.isEmpty()) {
            return;
        }

        for (Basket basket : baskets) {
            BasketRunnerTask basketRunnerTask = new BasketRunnerTask(getTestClass(), method, basket);
            runnerTasks.add(basketRunnerTask);
        }
    }

    private List<Basket> baskets(FrameworkMethod method) {
        DataFile dataFile = method.getAnnotation(DataFile.class);
        String filePath = dataFile.value();
        ArmyantJunitBasket basket = ArmyantJunitBasket.build(filePath);
        if (basket != null){
            List<Basket> datas = basket.getDatas();
            return datas;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    protected List<RunnerTask> getChildren() {
        if (runnerTasks == null){
            runnerTasks = Collections.unmodifiableList(createWalker());
        }
        return runnerTasks;
    }

    @Override
    protected Description describeChild(RunnerTask child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(RunnerTask runnerTask, RunNotifier notifier) {
        FrameworkMethod frameworkMethod = runnerTask.getFrameworkMethod();
        Description description = runnerTask.getDescription();
        if (isIgnored(frameworkMethod)) {
            // 被忽略的测试方法
            notifier.fireTestIgnored(description);
        } else {
            // 正常可被测试的方法
            runLeaf(methodBlock(runnerTask), description, notifier);
        }
    }

    protected boolean isIgnored(FrameworkMethod child) {
        return child.getAnnotation(Ignore.class) != null;
    }

    @Override
    protected void collectInitializationErrors(List<Throwable> errors) {
        super.collectInitializationErrors(errors);

        validateNoNonStaticInnerClass(errors);
        validateConstructor(errors);
        validateInstanceMethods(errors);
        validateFields(errors);
        validateMethods(errors);
    }

    protected void validateNoNonStaticInnerClass(List<Throwable> errors) {
        if (getTestClass().isANonStaticInnerClass()) {
            String gripe = "The inner class " + getTestClass().getName() + " is not static.";
            errors.add(new Exception(gripe));
        }
    }

    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
        validateZeroArgConstructor(errors);
    }

    protected void validateOnlyOneConstructor(List<Throwable> errors) {
        if (!hasOneConstructor()) {
            String gripe = "Test class should have exactly one public constructor";
            errors.add(new Exception(gripe));
        }
    }

    protected void validateZeroArgConstructor(List<Throwable> errors) {
        if (!getTestClass().isANonStaticInnerClass()
                && hasOneConstructor()
                && (getTestClass().getOnlyConstructor().getParameterTypes().length != 0)) {
            String gripe = "Test class should have exactly one public zero-argument constructor";
            errors.add(new Exception(gripe));
        }
    }

    private boolean hasOneConstructor() {
        return getTestClass().getJavaClass().getConstructors().length == 1;
    }

    @Deprecated
    protected void validateInstanceMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(After.class, false, errors);
        validatePublicVoidNoArgMethods(Before.class, false, errors);
        validateTestMethods(errors);

        if (getChildren().size() == 0) {
            errors.add(new Exception("No runnable methods"));
        }
    }

    protected void validateFields(List<Throwable> errors) {
        RULE_VALIDATOR.validate(getTestClass(), errors);
    }

    private void validateMethods(List<Throwable> errors) {
        RULE_METHOD_VALIDATOR.validate(getTestClass(), errors);
    }

    protected void validateTestMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(Test.class, false, errors);
    }

    protected Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance();
    }

    protected Statement methodBlock(RunnerTask task) {
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

        Statement statement = methodInvoker(task, test);
        statement = possiblyExpectingExceptions(task, test, statement);
        statement = withPotentialTimeout(task, test, statement);
        statement = withBefores(task, test, statement);
        statement = withAfters(task, test, statement);
        statement = withRules(task, test, statement);
        return statement;
    }

    protected Statement methodInvoker(RunnerTask task, Object test) {
        return task.createStatement(test);
    }

    protected Statement possiblyExpectingExceptions(RunnerTask task, Object test, Statement next) {
        Test annotation = task.getFrameworkMethod().getAnnotation(Test.class);
        return expectsException(annotation) ? new ExpectException(next, getExpectedException(annotation)) : next;
    }

    @Deprecated
    protected Statement withPotentialTimeout(RunnerTask task, Object test, Statement next) {
        long timeout = getTimeout(task.getFrameworkMethod().getAnnotation(Test.class));
        if (timeout <= 0) {
            return next;
        }
        return FailOnTimeout.builder().withTimeout(timeout, TimeUnit.MILLISECONDS).build(next);
    }

    protected Statement withBefores(RunnerTask task, Object target, Statement statement) {
        List<FrameworkMethod> befores = getTestClass().getAnnotatedMethods(Before.class);
        return befores.isEmpty() ? statement : new RunBefores(statement, befores, target);
    }

    protected Statement withAfters(RunnerTask task, Object target, Statement statement) {
        List<FrameworkMethod> afters = getTestClass().getAnnotatedMethods(After.class);
        return afters.isEmpty() ? statement : new RunAfters(statement, afters, target);
    }

    private Statement withRules(RunnerTask task, Object target, Statement statement) {
        List<TestRule> testRules = getTestRules(target);
        Statement result = statement;
        result = withMethodRules(task, testRules, target, result);
        result = withTestRules(task, testRules, result);
        return result;
    }

    private Statement withMethodRules(RunnerTask task, List<TestRule> testRules, Object target, Statement result) {
        for (org.junit.rules.MethodRule each : getMethodRules(target)) {
            if (!testRules.contains(each)) {
                result = each.apply(result, task.getFrameworkMethod(), target);
            }
        }
        return result;
    }

    private List<org.junit.rules.MethodRule> getMethodRules(Object target) {
        return rules(target);
    }

    protected List<MethodRule> rules(Object target) {
        List<MethodRule> rules = getTestClass().getAnnotatedMethodValues(target, Rule.class, MethodRule.class);
        rules.addAll(getTestClass().getAnnotatedFieldValues(target, Rule.class, MethodRule.class));
        return rules;
    }

    private Statement withTestRules(RunnerTask task, List<TestRule> testRules, Statement statement) {
        return testRules.isEmpty() ? statement : new RunRules(statement, testRules, describeChild(task));
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