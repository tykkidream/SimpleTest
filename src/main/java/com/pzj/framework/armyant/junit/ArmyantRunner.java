package com.pzj.framework.armyant.junit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pzj.framework.armyant.junit.annotaions.WalkerCase;
import com.pzj.framework.armyant.junit.walks.FrameworkMethodWalker;
import com.pzj.framework.armyant.junit.walks.LongDistanceWalker;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2017-1-4.
 */
public class ArmyantRunner extends ParentRunner<Walker> implements RunnerRun {
    private static Logger logger = LoggerFactory.getLogger(ArmyantRunner.class);

    private final List<Walker> walkers;

    public ArmyantRunner(Class<?> clazz) throws Throwable {
        super(clazz);
        walkers = Collections.unmodifiableList(createWalker());
    }

    private List<Walker> createWalker(){
        List<FrameworkMethod> testMethods = getTestClass().getAnnotatedMethods(Test.class);
        List<FrameworkMethod> testWalkers = getTestClass().getAnnotatedMethods(WalkerCase.class);

        List<Walker> walkers = new ArrayList<>(testMethods.size());
        for (FrameworkMethod method : testMethods){
            Walker walker;
            if (testWalkers.contains(method)){
                walker = new LongDistanceWalker(method);
            } else {
                walker = new FrameworkMethodWalker(getTestClass(), method);
            }
            walkers.add(walker);
        }

        return walkers;
    }

    @Override
    protected List<Walker> getChildren() {
        return walkers;
    }

    @Override
    protected Description describeChild(Walker child) {
        return child.getDescription();
    }

    @Override
    protected void runChild(Walker walker, RunNotifier notifier) {
        walker.walk(this, notifier);
    }

    public void run(Statement statement, Description description,
                                 RunNotifier notifier) {
        super.runLeaf(statement, description, notifier);
    }
}
