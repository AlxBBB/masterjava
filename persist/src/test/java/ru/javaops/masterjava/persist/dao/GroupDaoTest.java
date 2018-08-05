package ru.javaops.masterjava.persist.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.ProjecGrouptTestData;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;


import java.util.List;

import static ru.javaops.masterjava.persist.ProjecGrouptTestData.*;


public class GroupDaoTest extends AbstractDaoTest<GroupDao> {

  public GroupDaoTest() {
    super(GroupDao.class);
  }

  @BeforeClass
  public static void init() throws Exception {
    ProjecGrouptTestData.init();
  }

  @Before
  public void setUp() throws Exception {
    ProjecGrouptTestData.setUp();
  }

  @Test
  public void getAll() {
    List<Group> groups=dao.getAll();
    Assert.assertEquals(someGroups, groups);
  }

  @Test
  public void insert() throws Exception {
    dao.insert(new Group("topjava06",GroupType.FINISHED,topJavaId));
    Assert.assertEquals(3, dao.getAll().size());
  }

  @Test
  public void insertExists() throws Exception {
    dao.insert(topJava08);
    Assert.assertEquals(2, dao.getAll().size());
  }

  @Test
  public void findId() throws Exception {
    Assert.assertEquals(topJava07Id, dao.get("topjava07").getId().intValue());
  }

  @Test
  public void noFindId() throws Exception {
    Assert.assertNull(dao.get("gurujava000"));
  }


}