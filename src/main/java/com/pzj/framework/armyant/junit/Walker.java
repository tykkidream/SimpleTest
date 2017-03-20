package com.pzj.framework.armyant.junit;

import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * Created by Saber on 2017/3/18.
 */
public abstract class Walker implements Describable {

    public abstract Description getDescription();

    public int testCount() {
            return getDescription().testCount();
        }

    public abstract void walk(RunnerRun runnerRun, RunNotifier notifier);
}
