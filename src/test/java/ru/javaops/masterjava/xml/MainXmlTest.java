package ru.javaops.masterjava.xml;

import java.io.IOException;
import java.util.List;
import javax.xml.transform.TransformerException;
import org.junit.Test;
import ru.javaops.masterjava.xml.schema.User;

public class MainXmlTest {

  @Test
  public void getUsersByProject() throws Exception {
    List<User> users;
    System.out.println("Stax");
    users = MainXml.getUsersByProjectStAX("TopJava");
    if (users != null) {
      users.forEach(u -> System.out.println(u.getFullName() + " " + u.getEmail()));
    }
    System.out.println("JAXB");
    users = MainXml.getUsersByProjectJAXB("BaseJava");
    if (users != null) {
      users.forEach(u -> System.out.println(u.getFullName() + " " + u.getEmail()));
    }
  }

  @Test
  public void usersHtml() throws IOException, TransformerException {
    MainXml.usersHtml();
  }

  @Test
  public void groupHtml() throws IOException, TransformerException {
    MainXml.groupsHtml("MasterJava");
  }

}