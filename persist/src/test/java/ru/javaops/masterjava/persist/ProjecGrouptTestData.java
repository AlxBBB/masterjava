package ru.javaops.masterjava.persist;

import java.util.ArrayList;
import java.util.List;

import ru.javaops.masterjava.persist.dao.GroupDao;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Group;
import ru.javaops.masterjava.persist.model.GroupType;
import ru.javaops.masterjava.persist.model.Project;

public class ProjecGrouptTestData {

  public static Project topJava;
  public static int topJavaId;
  public static Project masterJava;
  public static List<Project> someProjects = new ArrayList<>();
  public static Group topJava07;
  public static Group topJava08;
  public static Group masterJava01;
  public static int topJava07Id;
  public static List<Group> someGroups = new ArrayList<>();

  public static void init() {
    topJava = new Project("topjava", "Topjava");
    masterJava = new Project("masterjava");
    topJava07=new Group("topjava07",GroupType.FINISHED,0);
    topJava08=new Group("topjava08",GroupType.CURRENT,0);
  }

  public static void setUp() {
    ProjectDao projectDao = DBIProvider.getDao(ProjectDao.class);
    projectDao.clean();
    DBIProvider.getDBI().useTransaction((conn, status) -> {
      topJavaId = projectDao.insert(topJava);
      topJava.setId(topJavaId);
      someProjects.clear();
      someProjects.add(topJava);
    });
    topJava07.setProjectId(topJavaId);
    topJava08.setProjectId(topJavaId);

    GroupDao groupDao=DBIProvider.getDao(GroupDao.class);
    groupDao.clean();;
    DBIProvider.getDBI().useTransaction((conn, status) -> {
      topJava07Id = groupDao.insert(topJava07);
      topJava07.setId(topJava07Id);
      topJava08.setId(groupDao.insert(topJava08));
      someGroups.clear();
      someGroups.add(topJava07);
      someGroups.add(topJava08);
    });
  }
}
