package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.junit.RunnerTask;
import org.junit.internal.runners.statements.InvokeMethod;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Created by Saber on 2017/3/18.
 */
public class SimpleRunnerTask extends RunnerTask {
    public SimpleRunnerTask(TestClass testClass, FrameworkMethod frameworkMethod){
        super(testClass, frameworkMethod);
    }

    @Override
    public Statement createStatement(Object test) {
        return new InvokeMethod(getFrameworkMethod(), test);
    }
}
