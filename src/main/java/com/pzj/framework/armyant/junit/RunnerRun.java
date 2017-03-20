package com.pzj.framework.armyant.junit;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.Statement;

/**
 * Created by Saber on 2017/3/18.
 */
public interface RunnerRun {

    void run(Statement statement, Description description, RunNotifier notifier);
}
