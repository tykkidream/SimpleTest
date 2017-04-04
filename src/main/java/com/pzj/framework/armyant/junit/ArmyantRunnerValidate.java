package com.pzj.framework.armyant.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import static org.junit.internal.runners.rules.RuleMemberValidator.RULE_METHOD_VALIDATOR;
import static org.junit.internal.runners.rules.RuleMemberValidator.RULE_VALIDATOR;

/**
 * Created by Saber on 2017/4/4.
 */
public class ArmyantRunnerValidate {
    private ArmyantRunner runner;

    public ArmyantRunnerValidate(ArmyantRunner runner){
        this.runner = runner;
    }

    public void validate() throws InitializationError {
        // 错误的集合
        List<Throwable> errors = new ArrayList<>();

        collectInitializationErrors(errors);
        if (!errors.isEmpty()) {
            throw new InitializationError(errors);
        }
    }

    protected void collectInitializationErrors(List<Throwable> errors) {
        validateNoNonStaticInnerClass(errors);
        validateConstructor(errors);
        validateInstanceMethods(errors);
        validateFields(errors);
        validateMethods(errors);
    }

    protected void validateNoNonStaticInnerClass(List<Throwable> errors) {
        if (runner.getTestClass().isANonStaticInnerClass()) {
            String gripe = "The inner class " + runner.getTestClass().getName() + " is not static.";
            errors.add(new Exception(gripe));
        }
    }

    protected void validateConstructor(List<Throwable> errors) {
        validateOnlyOneConstructor(errors);
        validateZeroArgConstructor(errors);
    }

    protected void validateOnlyOneConstructor(List<Throwable> errors) {
        if (!hasOneConstructor()) {
            String gripe = "Test class should have exactly one public constructor";
            errors.add(new Exception(gripe));
        }
    }

    protected void validateZeroArgConstructor(List<Throwable> errors) {
        if (!runner.getTestClass().isANonStaticInnerClass()
                && hasOneConstructor()
                && (runner.getTestClass().getOnlyConstructor().getParameterTypes().length != 0)) {
            String gripe = "Test class should have exactly one public zero-argument constructor";
            errors.add(new Exception(gripe));
        }
    }

    private boolean hasOneConstructor() {
        return runner.getTestClass().getJavaClass().getConstructors().length == 1;
    }

    @Deprecated
    protected void validateInstanceMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(After.class, false, errors);
        validatePublicVoidNoArgMethods(Before.class, false, errors);
        validateTestMethods(errors);

        if (runner.getChildren().size() == 0) {
            errors.add(new Exception("No runnable methods"));
        }
    }

    protected void validateFields(List<Throwable> errors) {
        RULE_VALIDATOR.validate(runner.getTestClass(), errors);
    }

    private void validateMethods(List<Throwable> errors) {
        RULE_METHOD_VALIDATOR.validate(runner.getTestClass(), errors);
    }

    protected void validateTestMethods(List<Throwable> errors) {
        validatePublicVoidNoArgMethods(Test.class, false, errors);
    }

    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation,
                                                  boolean isStatic, List<Throwable> errors) {
        List<FrameworkMethod> methods = runner.getTestClass().getAnnotatedMethods(annotation);

        // 迭代每个方法
        for (FrameworkMethod eachTestMethod : methods) {
            // 检验方法是公共的、无返回值的
            eachTestMethod.validatePublicVoid(isStatic, errors);
        }
    }
}
