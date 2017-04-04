package com.pzj.framework.armyant.junit.runtask;

import com.pzj.framework.armyant.junit.BasketStatement;
import com.pzj.framework.armyant.load.Basket;
import org.junit.runner.Description;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

/**
 * Created by Saber on 2017/3/18.
 */
public class BasketRunTask extends SimpleRunTask {
    private Basket basket;

    private int index;

    public BasketRunTask(TestClass testClass, FrameworkMethod frameworkMethod, Basket basket, int index) {
        super(testClass, frameworkMethod);
        setBasket(basket);
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
            description = Description.createTestDescription(testClass.getJavaClass(), frameworkMethod.getName() + ":" + index,  frameworkMethod.getAnnotations());
        }
        return description;
    }

    private static DescriptionUniqueId uniqueId(String methodName, String className, int index){
        String parentUniqued = formatDisplayName(methodName, methodName);
        String selftUniqued = formatDisplayName(methodName, methodName, index);

        DescriptionUniqueId uniqueId = new DescriptionUniqueId(parentUniqued, selftUniqued);
        return uniqueId;
    }


    public Statement methodInvoker(Object target) {
        return new BasketStatement(getFrameworkMethod(), target, getBasket());
    }
}
