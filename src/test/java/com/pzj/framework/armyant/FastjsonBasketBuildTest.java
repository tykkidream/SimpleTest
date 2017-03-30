package com.pzj.framework.armyant;

import com.pzj.framework.armyant.demo.Car;
import com.pzj.framework.armyant.demo.User;
import com.pzj.framework.armyant.load.Basket;
import com.pzj.framework.armyant.load.fastjson.FastjsonLoaderBuild;
import com.pzj.framework.armyant.load.fastjson.FastjsonResource;
import com.pzj.framework.armyant.load.fastjson.parsers.*;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Saber on 2017/3/18.
 */
public class FastjsonBasketBuildTest {
    @Test
    public void data_01(){
        FastjsonResource resource = new FastjsonResource("/data/data_01.json");

        BaseParser parser = new BaseParser();
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        Integer data = (Integer)(basket.get());

        Integer expected = 123456789;

        assertNotNull(data);
        assertEquals(expected, data);
    }

    @Test
    public void data_01_byget(){
        FastjsonResource resource = new FastjsonResource("/data/data_01.json");

        {
            BaseParser parser = new BaseParser(Integer.class);
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            Integer data = (Integer) basket.get();
            Integer expected = 123456789;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            BaseParser parser = new BaseParser(Long.class);
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            Long data = (Long) basket.get();
            Long expected = 123456789L;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            BaseParser parser = new BaseParser(String.class);
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            String data = (String) basket.get();
            String expected = "123456789";
            assertNotNull(data);
            assertEquals(expected, data);
        }
    }


    @Test
    public void data_02(){
        FastjsonResource resource = new FastjsonResource("/data/data_02.json");

        BaseParser parser = new BaseParser(String.class);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        String data = (String)(basket.get());

        String expected = "hello world";

        assertNotNull(data);
        assertEquals(expected, data);
    }

    @Test
    public void data_03(){
        FastjsonResource resource = new FastjsonResource("/data/data_03.json");

        BaseParser parser = new BaseParser(Integer.class);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        List<Integer> data = (List<Integer>)(basket.get());

        Integer expected1 = 123456789;
        Integer expected2 = 223456789;
        Integer expected3 = 323456789;

        assertNotNull(data);
        assertTrue(data instanceof ArrayList);
        assertTrue(data.contains(expected1));
        assertTrue(data.contains(expected2));
        assertTrue(data.contains(expected3));
        assertFalse(data.contains("123456789"));
    }

    @Test
    public void data_03_string(){
        FastjsonResource resource = new FastjsonResource("/data/data_03.json");

        BaseParser parser = new BaseParser(String.class);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        List<String> data = (List<String>)(basket.get());

        String expected1 = "123456789";
        String expected2 = "223456789";
        String expected3 = "323456789";

        assertNotNull(data);
        assertTrue(data instanceof ArrayList);
        assertTrue(data.contains(expected1));
        assertTrue(data.contains(expected2));
        assertTrue(data.contains(expected3));
        assertFalse(data.contains(new Integer("123456789")));
    }

    @Test
    public void data_03_long(){
        FastjsonResource resource = new FastjsonResource("/data/data_03.json");

        BaseParser parser = new BaseParser(Long.class);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        List<Long> data = (List<Long>)(basket.get());

        Long expected1 = 123456789L;
        Long expected2 = 223456789L;
        Long expected3 = 323456789L;

        assertNotNull(data);
        assertTrue(data instanceof ArrayList);
        assertTrue(data.contains(expected1));
        assertTrue(data.contains(expected2));
        assertTrue(data.contains(expected3));
        assertFalse(data.contains(new Integer("123456789")));
    }

    @Test
    public void data_04(){
        FastjsonResource resource = new FastjsonResource("/data/data_04.json");

        BaseParser parser = new BaseParser(String.class);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        List<String> data = (List<String>)(basket.get());

        String expected1 = "hello world";
        String expected2 = "hello tom";
        String expected3 = "hello smith";

        assertNotNull(data);
        assertTrue(data instanceof ArrayList);
        assertTrue(data.contains(expected1));
        assertTrue(data.contains(expected2));
        assertTrue(data.contains(expected3));
        assertFalse(data.contains("hi"));
    }

    @Test
    public void data_05_getBy() throws ParseException {
        FastjsonResource resource = new FastjsonResource("/data/data_05.json");

        MultiParser parser = new MultiParser();

        {
            parser.registerParser("id", new BaseParser(Integer.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            Integer data = (Integer) basket.get("id");
            Integer expected = 1;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("id", new BaseParser(Long.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            Long data = (Long) basket.get("id");
            Long expected = 1L;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("id", new BaseParser(String.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            String data = (String) basket.get("id");
            String expected = "1";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("nickname", new BaseParser(String.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            String data = (String) basket.get("nickname");
            String expected = "aabb";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("password", new BaseParser(Integer.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            Integer data = (Integer) basket.get("password");
            Integer expected = 123456;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("password", new BaseParser(String.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            String data = (String) basket.get("password");
            String expected = "123456";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("sex", new BaseParser(Integer.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            Integer data = (Integer) basket.get("sex");
            Integer expected = 1;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("sex", new BaseParser(String.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            String data = (String) basket.get("sex");
            String expected = "1";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            parser.registerParser("birthDay", new BaseParser(String.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            String data = (String) basket.get("birthDay");
            String expected = "2016-10-21";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        /*{
            Date data = loader.get("birthDay", Date.class);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date expected = sdf.parse("2016-10-21");
            assertNotNull(data);
            assertEquals(expected, data);
        }*/
        {
            parser.registerParser("aa", new BaseParser(String.class));
            Basket basket = FastjsonLoaderBuild.build(resource, parser);
            String data = (String) basket.get("aa");
            assertNull(data);
        }
    }

    @Test
    public void data_05() throws ParseException {
        FastjsonResource resource = new FastjsonResource("/data/data_05.json");

        BeanParser parser = new BeanParser(User.class);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        User data = (User) basket.get();

        Long expectedId = 1L;
        String expectedUsername = "tomcat";
        String expectedNickname = "aabb";
        String expectedPassword = "123456";
        Integer expectedSex = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedBirthDay = sdf.parse("2016-10-21");

        assertNotNull(data);
        assertEquals(expectedId, data.getId());
        assertEquals(expectedUsername, data.getUsername());
        assertEquals(expectedNickname, data.getNickname());
        assertEquals(expectedPassword, data.getPassword());
        assertEquals(expectedSex, data.getSex());
        assertEquals(expectedBirthDay, data.getBirthDay());

    }


    @Test
    public void data_06() throws ParseException {
        FastjsonResource resource = new FastjsonResource("/data/data_06.json");

        BeanParser parser = new BeanParser(User.class);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        List<User> data = (List<User>) basket.get();

        assertNotNull(data);
        assertTrue(data.size() == 1);

        User user = data.get(0);

        Long expectedId = 1L;
        String expectedUsername = "tomcat";
        String expectedNickname = "aabb";
        String expectedPassword = "123456";
        Integer expectedSex = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedBirthDay = sdf.parse("2016-10-21");

        assertNotNull(data);
        assertEquals(expectedId, user.getId());
        assertEquals(expectedUsername, user.getUsername());
        assertEquals(expectedNickname, user.getNickname());
        assertEquals(expectedPassword, user.getPassword());
        assertEquals(expectedSex, user.getSex());
        assertEquals(expectedBirthDay, user.getBirthDay());

    }

    @Test
    public void data_07() throws ParseException {
        FastjsonResource resource = new FastjsonResource("/data/data_07.json");

        MultiParser parser = new MultiParser();
        parser.registerParser("user", new BeanParser(User.class));
        Basket basket = FastjsonLoaderBuild.build(resource, parser);
        User data = (User) basket.get("user");

        Long expectedId = 1L;
        String expectedUsername = "tomcat";
        String expectedNickname = "aabb";
        String expectedPassword = "123456";
        Integer expectedSex = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedBirthDay = sdf.parse("2016-10-21");

        assertNotNull(data);
        assertEquals(expectedId, data.getId());
        assertEquals(expectedUsername, data.getUsername());
        assertEquals(expectedNickname, data.getNickname());
        assertEquals(expectedPassword, data.getPassword());
        assertEquals(expectedSex, data.getSex());
        assertEquals(expectedBirthDay, data.getBirthDay());

    }

    @Test
    public void data_08() throws ParseException {
        FastjsonResource resource = new FastjsonResource("/data/data_08.json");

        MultiParser parser = new MultiParser();
        parser.registerParser("user", new BeanParser(User.class));
        Basket basket = FastjsonLoaderBuild.build(resource, parser);
        List<User> datas = (List<User>)basket.get("user");

        assertNotNull(datas);
        User data = datas.get(0);

        Long expectedId = 1L;
        String expectedUsername = "tomcat";
        String expectedNickname = "aabb";
        String expectedPassword = "123456";
        Integer expectedSex = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedBirthDay = sdf.parse("2016-10-21");

        assertEquals(expectedId, data.getId());
        assertEquals(expectedUsername, data.getUsername());
        assertEquals(expectedNickname, data.getNickname());
        assertEquals(expectedPassword, data.getPassword());
        assertEquals(expectedSex, data.getSex());
        assertEquals(expectedBirthDay, data.getBirthDay());
    }


    @Test
    public void data_09() throws ParseException {
        FastjsonResource resource = new FastjsonResource("/data/data_09.json");

        BeanParser beanParser = new BeanParser(User.class);

        BasketParser basketParser = new BasketParser();
        basketParser.registerParser("user", beanParser);

        MultiParser parser = new MultiParser();
        parser.registerParser("case1", basketParser);
        Basket basket = FastjsonLoaderBuild.build(resource, parser);
        assertNotNull(basket);

        Basket case1Basket = (Basket) basket.get("case1");
        assertNotNull(case1Basket);

        User data = (User) case1Basket.get("user");
        assertNotNull(data);

        Long expectedId = 1L;
        String expectedUsername = "tomcat";
        String expectedNickname = "aabb";
        String expectedPassword = "123456";
        Integer expectedSex = 1;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date expectedBirthDay = sdf.parse("2016-10-21");

        assertEquals(expectedId, data.getId());
        assertEquals(expectedUsername, data.getUsername());
        assertEquals(expectedNickname, data.getNickname());
        assertEquals(expectedPassword, data.getPassword());
        assertEquals(expectedSex, data.getSex());
        assertEquals(expectedBirthDay, data.getBirthDay());

    }


    @Test
    public void data_19() throws ParseException {
        FastjsonResource resource = new FastjsonResource("/data/data_10.json");

        SchemaParser parser = new SchemaParser();
        parser.registerParserSchema("data", "dataClass");
        parser.registerParserSchema("case", "caseClass");

        Basket basket = FastjsonLoaderBuild.build(resource, parser);

        assertNotNull(basket);

        Object d = basket.get("data");
        assertTrue(d instanceof User);

        Object c = basket.get("case");
        assertTrue(c instanceof Car);

    }
}
