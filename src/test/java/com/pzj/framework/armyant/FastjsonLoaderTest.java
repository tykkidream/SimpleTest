package com.pzj.framework.armyant;

import com.pzj.framework.armyant.demo.User;
import com.pzj.framework.armyant.load.fastjson.FastjsonLoader;
import com.pzj.framework.armyant.load.fastjson.FastjsonResource;
import com.pzj.framework.armyant.load.fastjson.parsers.VarietyParser;
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
public class FastjsonLoaderTest {
    @Test
    public void data_01(){
        FastjsonResource dataMap = new FastjsonResource("/data/data_01.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap);

        Integer data = (Integer)(loader.get());

        Integer expected = 123456789;

        assertNotNull(data);
        assertEquals(expected, data);
    }

    @Test
    public void data_01_byget(){
        FastjsonResource dataMap = new FastjsonResource("/data/data_01.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap);

        {
            Integer data = loader.get(Integer.class);
            Integer expected = 123456789;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            Long data = loader.get(Long.class);
            Long expected = 123456789L;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = loader.get(String.class);
            String expected = "123456789";
            assertNotNull(data);
            assertEquals(expected, data);
        }
    }

    @Test
    public void data_01_string(){
        FastjsonResource dataMap = new FastjsonResource("/data/data_01.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap, String.class);

        String data = (String) (loader.get());

        String expected = "123456789";

        assertNotNull(data);
        assertEquals(expected, data);
    }

    @Test
    public void data_01_long(){
        FastjsonResource dataMap = new FastjsonResource("/data/data_01.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap, Long.class);

        Long data = (Long) (loader.get());

        Long expected = 123456789L;

        assertNotNull(data);
        assertEquals(expected, data);
    }

    @Test
    public void data_02(){
        FastjsonResource dataMap = new FastjsonResource("/data/data_02.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap);

        String data = (String)(loader.get());

        String expected = "hello world";

        assertNotNull(data);
        assertEquals(expected, data);
    }

    @Test
    public void data_03(){
        FastjsonResource dataMap = new FastjsonResource("/data/data_03.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap);

        List<Integer> data = (List<Integer>)(loader.get());

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
        FastjsonResource dataMap = new FastjsonResource("/data/data_03.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap, String.class);

        List<String> data = (List<String>)(loader.get());

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
        FastjsonResource dataMap = new FastjsonResource("/data/data_03.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap, Long.class);

        List<Long> data = (List<Long>)(loader.get());

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
        FastjsonResource dataMap = new FastjsonResource("/data/data_04.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap);

        List<String> data = (List<String>)(loader.get());

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
        FastjsonResource dataMap = new FastjsonResource("/data/data_05.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap);

        {
            Integer data = (Integer) loader.get("id");
            Integer expected = 1;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            Long data = loader.get("id", Long.class);
            Long expected = 1L;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = loader.get("id", String.class);
            String expected = "1";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = (String)loader.get("nickname");
            String expected = "aabb";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = loader.get("nickname", String.class);
            String expected = "aabb";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = (String) loader.get("password");
            String expected = "123456";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = loader.get("password", String.class);
            String expected = "123456";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = (String) loader.get("sex");
            String expected = "1";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            Integer data = loader.get("sex", Integer.class);
            Integer expected = 1;
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = loader.get("birthDay", String.class);
            String expected = "2016-10-21";
            assertNotNull(data);
            assertEquals(expected, data);
        }
        {
            String data = (String) loader.get("birthDay");
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
            String data = loader.get("aa", String.class);
            assertNull(data);
        }
    }

    @Test
    public void data_05() throws ParseException {
        FastjsonResource dataMap = new FastjsonResource("/data/data_05.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap, User.class);

        User data = (User)loader.get();

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
        FastjsonResource dataMap = new FastjsonResource("/data/data_06.json");

        FastjsonLoader loader = new FastjsonLoader();
        loader.parser(dataMap, User.class);

        List<User> data = (List<User>)loader.get();

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
        FastjsonResource dataMap = new FastjsonResource("/data/data_07.json");

        FastjsonLoader loader = new FastjsonLoader();

        loader.parser(dataMap, User.class);

        {
            User data = (User) loader.get();
            assertNull(data.getId());
        }

        {
            User data = loader.get("user", User.class);

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

        {
            VarietyParser parser = new VarietyParser();
            parser.registerParser("user", User.class);
            loader.parser(dataMap, parser);

            User data = (User) loader.get("user");

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

    }

}
