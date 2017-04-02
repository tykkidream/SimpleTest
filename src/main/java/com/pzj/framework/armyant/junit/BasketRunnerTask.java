package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.load.Basket;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Created by Saber on 2017/3/18.
 */
public class BasketRunnerTask extends RunnerTask {
    private Basket basket;

    private int index;

    public BasketRunnerTask(TestClass testClass, FrameworkMethod frameworkMethod, Basket basket, int index) {
        super(testClass, frameworkMethod);
        this.basket = basket;
        this.index = index;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Override
    public Description getDescription() {
        if (description == null){
            description = Description.createTestDescription(testClass.getJavaClass(),
                    frameworkMethod.getName() + ":" + index , frameworkMethod.getAnnotations());
        }
        return description;
    }

    @Override
    public Statement createStatement(Object test) {
        return new BasketStatement(getFrameworkMethod(), test, getBasket());
    }
}
