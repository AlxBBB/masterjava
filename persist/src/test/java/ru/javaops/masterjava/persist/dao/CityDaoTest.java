package ru.javaops.masterjava.persist.dao;

import static ru.javaops.masterjava.persist.CityTestData.kiev;
import static ru.javaops.masterjava.persist.CityTestData.minsk;
import static ru.javaops.masterjava.persist.CityTestData.moscowId;
import static ru.javaops.masterjava.persist.CityTestData.someCities;
import static ru.javaops.masterjava.persist.UserTestData.FIST5_USERS;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.CityTestData;
import ru.javaops.masterjava.persist.model.City;

public class CityDaoTest extends AbstractDaoTest<CityDao> {

  public CityDaoTest() {
    super(CityDao.class);
  }

  @BeforeClass
  public static void init() throws Exception {
    CityTestData.init();
  }

  @Before
  public void setUp() throws Exception {
    CityTestData.setUp();
  }

  @Test
  public void getAll() {
    List<City> cities = dao.getAll();
    Assert.assertEquals(someCities, cities);
  }

  @Test
  public void insert() throws Exception {
    dao.insert(kiev);
    Assert.assertEquals(3, dao.getAll().size());
  }

  @Test
  public void insertExists() throws Exception {
    dao.insert(minsk);
    Assert.assertEquals(2, dao.getAll().size());
  }

  @Test
  public void findId() throws Exception {
    Assert.assertEquals(moscowId, dao.get("mow").getId().intValue());
  }

  @Test
  public void noFindId() throws Exception {
    Assert.assertNull(dao.get("msk"));
  }


}