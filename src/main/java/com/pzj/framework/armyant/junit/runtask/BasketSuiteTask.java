package com.pzj.framework.armyant.junit.runtask;

import com.pzj.framework.armyant.junit.RunTask;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import java.util.List;

/**
 * Created by Saber on 2017/4/4.
 */
public class BasketSuiteTask extends SimpleRunTask implements RunTask {
    private List<RunTask> children = null;

    public BasketSuiteTask(TestClass testClass, FrameworkMethod frameworkMethod, List<RunTask> children) {
        super(testClass, frameworkMethod);
        this.children = children;
    }

    @Override
    public Description getDescription() {
        if (description == null){
            // description = Description.createTestDescription(testClass.getJavaClass(), frameworkMethod.getName(), frameworkMethod.getAnnotations());
            description = Description.createSuiteDescription(formatDisplayName(frameworkMethod.getName(), testClass.getName()), frameworkMethod.getAnnotations());
            addChildenDescription(description, children);
        }
        return description;
    }

    public List<RunTask> getChildren() {
        return children;
    }

    public void setChildren(List<RunTask> children){
        this.children = children;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public Statement createStatement(Object target) {
        return null;
    }
}
