package com.pzj.framework.armyant.junit.walks;

import com.pzj.framework.armyant.OneTestConfiguration;
import com.pzj.framework.armyant.OneTestConfigurationBuilder;
import com.pzj.framework.armyant.junit.RunnerRun;
import com.pzj.framework.armyant.junit.Walker;
import com.pzj.framework.armyant.junit.annotaions.DataFile;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;

import java.util.List;

/**
 * Created by Saber on 2017/3/18.
 */
public class LongDistanceWalker extends Walker {
    private Description description;

    private FrameworkMethod frameworkMethod;

    private List<Walker> walkers;

    public LongDistanceWalker(FrameworkMethod frameworkMethod) {
        this.frameworkMethod = frameworkMethod;
    }

    private void aa(){
        DataFile dataFile = frameworkMethod.getAnnotation(DataFile.class);
        String filePath = dataFile.value();

        OneTestConfiguration oneTestConfiguration = OneTestConfigurationBuilder.build(filePath);
    }

    @Override
    public Description getDescription() {
        return null;
    }

    @Override
    public void walk(RunnerRun runnerRun, RunNotifier notifier) {
        if (this.walkers != null) {
            for (Walker walker : walkers) {
                walker.walk(runnerRun, notifier);
            }
        }
    }
}
