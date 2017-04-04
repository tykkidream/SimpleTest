package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.junit.runtask.BasketSuiteTask;
import com.pzj.framework.armyant.junit.runtask.SimpleSuiteTask;
import org.junit.internal.runners.model.ReflectiveCallable;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017-1-4.
 */
public class ArmyantRunner extends ParentRunner<RunTask> {
    private static Logger logger = LoggerFactory.getLogger(ArmyantRunner.class);

    private SimpleSuiteTask runTask;

    public ArmyantRunner(Class<?> clazz) throws Throwable {
        super(clazz);
        init();
        validate();
    }

    private void init() {
        SimpleSuiteTask runTask = RunTaskBuilder.build(getTestClass());
        this.runTask = runTask;
    }

    private void validate() throws InitializationError {
        ArmyantRunnerValidate validate = new ArmyantRunnerValidate(this);
        validate.validate();
    }

    @Override
    protected List<RunTask> getChildren() {
        return runTask.getChildren();
    }

    @Override
    protected Description describeChild(RunTask child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(RunTask runTask, RunNotifier notifier) {
        if (runTask.isIgnored()){
            // 被忽略的测试方法
            notifier.fireTestIgnored(runTask.getDescription());
        } else if (runTask instanceof BasketSuiteTask){
            // 正常可被测试的方法：执行多个RunTask
            runSuite((BasketSuiteTask)runTask, notifier);
        } else {
            // 正常可被测试的方法：执行单个RunTask
            Statement statement = methodBlock(runTask);
            runLeaf(statement, runTask.getDescription(), notifier);
        }
    }

    private void runSuite(BasketSuiteTask runTask, RunNotifier notifier){
        List<RunTask> children = runTask.getChildren();
        for (RunTask childTask : children){
            runChild(childTask, notifier);
        }
    }

    private Statement methodBlock(RunTask runTask){
        Object target;
        try {
            target = new ReflectiveCallable() {
                @Override
                protected Object runReflectiveCall() throws Throwable {
                    return createTest();
                }
            }.run();

        } catch (Throwable e) {
            return new Fail(e);
        }
        return runTask.createStatement(target);
    }

    protected Object createTest() throws Exception {
        return getTestClass().getOnlyConstructor().newInstance();
    }

    public void filter(Filter filter) throws NoTestsRemainException {

        final Description desiredDescription = filterDesiredDescription(filter);
        Filter myFilter = new Filter(){
            @Override
            public boolean shouldRun(Description description) {
                boolean equals = desiredDescription.equals(description);
                if (equals){
                    return equals;
                }

                if (!description.isTest()) {
                    for (Description each : description.getChildren()) {
                        if (shouldRun(each)) {
                            return true;
                        }
                    }
                }

                return false;
            }

            @Override
            public String describe() {
                return String.format("Method %s", desiredDescription.getDisplayName());
            }
        };

        super.filter(myFilter);
    }

    private Description filterDesiredDescription(Filter filter){
        try {
            Field[] declaredFields = filter.getClass().getDeclaredFields();
            if (declaredFields == null || declaredFields.length == 0){
                return null;
            }

            for (int i =0; i < declaredFields.length; i++){
                Field field = declaredFields[i];
                field.setAccessible(true);

                if (Filter.class.isAssignableFrom(field.getType())){
                    Filter value = (Filter)field.get(filter);
                    Description description = filterDesiredDescription(value);
                    if (description != null){
                        return description;
                    }
                }
                if (Description.class.isAssignableFrom(field.getType())){
                    Description value = (Description)field.get(filter);
                    return value;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}