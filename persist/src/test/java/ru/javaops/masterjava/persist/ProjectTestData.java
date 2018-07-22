package ru.javaops.masterjava.persist;

import java.util.ArrayList;
import java.util.List;
import ru.javaops.masterjava.persist.dao.ProjectDao;
import ru.javaops.masterjava.persist.model.Project;

public class ProjectTestData {

  public static Project topJava;
  public static int topJavaId;
  public static Project masterJava;
  public static List<Project> someProjects = new ArrayList<>();


  public static void init() {
    topJava = new Project("topjava", "Topjava");
    masterJava = new Project("masterjava");
  }

  public static void setUp() {
    ProjectDao dao = DBIProvider.getDao(ProjectDao.class);
    dao.clean();
    DBIProvider.getDBI().useTransaction((conn, status) -> {
      topJavaId = dao.insert(topJava);
      topJava.setId(topJavaId);
      someProjects.clear();
      someProjects.add(topJava);
    });
  }
}
