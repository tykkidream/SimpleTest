package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.load.Basket;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Created by Saber on 2017/3/18.
 */
public class BasketRunnerTask extends RunnerTask {
    private Basket basket;

    public BasketRunnerTask(TestClass testClass, FrameworkMethod frameworkMethod, Basket basket) {
        super(testClass, frameworkMethod);
        this.basket = basket;
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Override
    public Statement createStatement(Object test) {
        return new BasketStatement(getFrameworkMethod(), test, getBasket());
    }
}
