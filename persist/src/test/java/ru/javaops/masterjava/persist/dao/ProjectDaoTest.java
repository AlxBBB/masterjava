package ru.javaops.masterjava.persist.dao;

import static ru.javaops.masterjava.persist.ProjectTestData.masterJava;
import static ru.javaops.masterjava.persist.ProjectTestData.someProjects;
import static ru.javaops.masterjava.persist.ProjectTestData.topJava;
import static ru.javaops.masterjava.persist.ProjectTestData.topJavaId;

import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.javaops.masterjava.persist.ProjectTestData;
import ru.javaops.masterjava.persist.model.Project;


public class ProjectDaoTest extends AbstractDaoTest<ProjectDao> {

  public ProjectDaoTest() {
    super(ProjectDao.class);
  }

  @BeforeClass
  public static void init() throws Exception {
    ProjectTestData.init();
  }

  @Before
  public void setUp() throws Exception {
    ProjectTestData.setUp();
  }

  @Test
  public void getAll() {
    List<Project> projects = dao.getAll();
    Assert.assertEquals(someProjects, projects);
  }

  @Test
  public void insert() throws Exception {
    dao.insert(masterJava);
    Assert.assertEquals(2, dao.getAll().size());
  }

  @Test
  public void insertExists() throws Exception {
    dao.insert(topJava);
    Assert.assertEquals(1, dao.getAll().size());
  }

  @Test
  public void findId() throws Exception {
    Assert.assertEquals(topJavaId, dao.get("topjava").getId().intValue());
  }

  @Test
  public void noFindId() throws Exception {
    Assert.assertNull(dao.get("gurujava"));
  }


}