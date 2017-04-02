package com.pzj.framework.armyant.junit;

import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Created by Saber on 2017/3/18.
 */
public abstract class RunnerTask implements Describable {

    protected Description description;

    protected FrameworkMethod frameworkMethod;

    protected TestClass testClass;

    public RunnerTask(TestClass testClass, FrameworkMethod frameworkMethod){
        this.testClass = testClass;
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

    public TestClass getTestClass() {
        return testClass;
    }

    public int testCount() {
        return getDescription().testCount();
    }

    public abstract Statement createStatement(Object test);
}
