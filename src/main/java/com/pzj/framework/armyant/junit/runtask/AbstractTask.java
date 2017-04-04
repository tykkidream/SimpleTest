package com.pzj.framework.armyant.junit.runtask;

import com.pzj.framework.armyant.junit.RunTask;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import java.util.List;

/**
 * Created by Saber on 2017/4/4.
 */
public abstract class AbstractTask implements RunTask {

    protected Description description;

    protected TestClass testClass;

    public AbstractTask(TestClass testClass){
        super();
        this.testClass = testClass;
    }

    public TestClass getTestClass() {
        return testClass;
    }

    @Override
    public boolean isIgnored() {
        return false;
    }

    @Override
    public boolean hasChilden() {
        return false;
    }

    @Override
    public List<RunTask> getChildren() {
        return null;
    }

    @Override
    public Statement createStatement(Object target) {
        return null;
    }

    protected static void addChildenDescription(Description parentDescription, List<RunTask> runTasks){
        if (runTasks != null){
            for (RunTask runTask : runTasks){
                parentDescription.addChild(runTask.getDescription());
            }
        }
    }

    protected static String formatDisplayName(String name, String className) {
        return String.format("%s(%s)", name, className);
    }

    protected static String formatDisplayName(String name, String className, int index) {
        return String.format("%s:%s(%s)", name, index, className);
    }

}
