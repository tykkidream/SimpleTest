package com.pzj.framework.armyant.junit;

import org.junit.runner.Describable;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

import java.util.List;

/**
 * Created by Saber on 2017/3/18.
 */
public interface RunTask extends Describable {
    Statement createStatement(Object target);

    boolean isIgnored();

    boolean hasChilden();

    List<RunTask> getChildren();
}
