package utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by yangwenjun on 2021/11/29 10:42
 */
public class StringLocalUtilsTest {

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void parse() {

    System.out.println(StringLocalUtils.parse("abc"));

    Assert.assertEquals("abc",StringLocalUtils.parse("abc"));

  }
}