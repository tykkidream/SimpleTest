package com.pzj.framework.armyant;

import com.pzj.framework.armyant.demo.Car;
import com.pzj.framework.armyant.demo.User;
import com.pzj.framework.armyant.junit.ArmyantRunner;
import com.pzj.framework.armyant.junit.DataFile;
import com.pzj.framework.armyant.junit.DataKey;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Saber on 2017/3/18.
 */
@RunWith(ArmyantRunner.class)
public class ArmyantRunnerTest {
    @Test
    public void test1(){
        System.out.println("hello world");
    }

    @Test
    public void test2(){
        System.out.println("hello world");
    }

    @Test
    @DataFile("/data/data_12.json")
    public void test3(){
        System.out.println("hello world");
    }

    @Test
    @DataFile("/data/data_12.json")
    public void test4(@DataKey("user") User user, @DataKey("car") Car car){
        System.out.println("hello world");
        System.out.println(user.getNickname());
        System.out.println(car.getName());
    }
}
