package com.pzj.framework.armyant.junit;

import com.pzj.framework.armyant.load.Basket;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Saber on 2017/3/18.
 */
public class BasketStatement extends Statement {
    private final FrameworkMethod testMethod;
    private final Object target;
    private Basket basket;

    public BasketStatement(FrameworkMethod testMethod, Object target, Basket basket) {
        this.testMethod = testMethod;
        this.target = target;
        this.basket = basket;
    }

    @Override
    public void evaluate() throws Throwable {
        Method method = testMethod.getMethod();

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        if (parameterAnnotations == null || parameterAnnotations.length == 0) {
            return;
        }

        Object[] params = new Object[method.getParameterTypes().length];

        A : for (int i = 0; i < parameterAnnotations.length; i++){
            Annotation[] annos = parameterAnnotations[i];
            B : for (int j = 0; j < annos.length; j++){
                Annotation anno = annos[j];
                if (anno instanceof DataKey){
                    DataKey testData = (DataKey) anno;
                    String dataKey = testData.value();
                    if ("".equals(dataKey)){
                        Object data = basket.get();
                        params[i] = data;
                        break A;
                    } else {
                        Object data = basket.get(dataKey);
                        params[i] = data;
                        break B;
                    }
                }
            }
        }

        testMethod.invokeExplosively(target, params);
    }
}
