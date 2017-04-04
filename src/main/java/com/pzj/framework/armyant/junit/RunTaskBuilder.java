package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.junit.load.ArmyantJunitBasket;
import com.pzj.framework.armyant.junit.runtask.BasketRunTask;
import com.pzj.framework.armyant.junit.runtask.BasketSuiteTask;
import com.pzj.framework.armyant.junit.runtask.SimpleRunTask;
import com.pzj.framework.armyant.junit.runtask.SimpleSuiteTask;
import com.pzj.framework.armyant.load.Basket;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saber on 2017/4/4.
 */
public class RunTaskBuilder {

    public static SimpleSuiteTask build(TestClass testClass) {
        List<FrameworkMethod> testMethods = testClass.getAnnotatedMethods(Test.class);
        List<FrameworkMethod> testWalkers = testClass.getAnnotatedMethods(DataFile.class);

        List<RunTask> runTasks = new ArrayList<>(testMethods.size());

        for (FrameworkMethod method : testMethods) {
            RunTask runTask;
            if (testWalkers.contains(method)) {
                runTask = createSuiteRunTask(testClass, method);
            } else {
                runTask = new SimpleRunTask(testClass, method);
            }

            if (runTask != null) {
                runTasks.add(runTask);
            }
        }

        SimpleSuiteTask runTask = new SimpleSuiteTask(testClass, runTasks);
        return runTask;
    }

    private static BasketSuiteTask createSuiteRunTask(TestClass testClass, FrameworkMethod method) {
        List<Basket> baskets = loadBaskets(method);

        if (baskets == null || baskets.isEmpty()) {
            return null;
        }

        int index = 0;
        List<RunTask> runTasks = new ArrayList<>(baskets.size());
        for (Basket basket : baskets) {
            BasketRunTask basketRunnerTask = new BasketRunTask(testClass, method, basket, index++);
            runTasks.add(basketRunnerTask);
        }

        BasketSuiteTask runTask = new BasketSuiteTask(testClass, method, runTasks);
        return runTask;
    }

    private static List<Basket> loadBaskets(FrameworkMethod method) {
        DataFile dataFile = method.getAnnotation(DataFile.class);
        String filePath = dataFile.value();
        ArmyantJunitBasket basket = ArmyantJunitBasket.build(filePath);
        if (basket != null){
            List<Basket> datas = basket.getDatas();
            return datas;
        }
        return null;
    }

}
