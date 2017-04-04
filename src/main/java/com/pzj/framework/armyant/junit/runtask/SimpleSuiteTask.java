package com.pzj.framework.armyant.junit.runtask;

import com.pzj.framework.armyant.junit.RunTask;
import org.junit.runner.Description;
import org.junit.runners.model.TestClass;

import java.util.List;

/**
 * Created by Saber on 2017/4/4.
 */
public class SimpleSuiteTask extends AbstractTask {
    private List<RunTask> children = null;

    public SimpleSuiteTask(TestClass testClass, List<RunTask> children) {
        super(testClass);
        setChildren(children);
    }

    @Override
    public Description getDescription() {
        if (description == null){
            description = Description.createSuiteDescription(testClass.getName(), testClass.getAnnotations());
             addChildenDescription(description, children);
        }
        return description;
    }

    public List<RunTask> getChildren() {
        return children;
    }

    public void setChildren(List<RunTask> children) {
        this.children = children;
    }
}
