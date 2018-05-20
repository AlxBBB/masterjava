package ru.javaops.masterjava.xml;


import com.google.common.io.Resources;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBElement;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.TransformerException;
import ru.javaops.masterjava.xml.schema.GroupType;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.ProjectType;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;
import ru.javaops.masterjava.xml.util.XsltProcessor;

public class MainXml {

  public final static String PROJECT_ELEMENT = "Project";
  public final static String GROUP_ELEMENT = "Group";
  public final static String USER_ELEMENT = "User";

  public static List<User> getUsersByProjectStAX(String project) throws Exception {
    if (project == null) {
      return null;
    }
    List<User> users = new ArrayList<>();
    try (StaxStreamProcessor processor = new StaxStreamProcessor(
        Resources.getResource("payload.xml").openStream())) {
      XMLStreamReader reader = processor.getReader();
      String projectId = processor.getIdByText(PROJECT_ELEMENT, project);
      Set<String> groups = new HashSet<>();
      while (reader.hasNext()) {
        int event = reader.next();
        if (event == XMLEvent.END_ELEMENT && GROUP_ELEMENT.equals(reader.getLocalName())) {
          break;
        }
        if (event == XMLEvent.START_ELEMENT && GROUP_ELEMENT.equals(reader.getLocalName())) {
          if (projectId.equals(reader.getAttributeValue(null, "project"))) {
            groups.add(reader.getAttributeValue(null, "id"));
          }
        }
      }
      if (groups.isEmpty()) {
        return null;
      }
      while (processor.doUntil(XMLEvent.START_ELEMENT, USER_ELEMENT)) {
        User user = new User();
        user.setEmail(reader.getAttributeValue(null, "email"));
        while (reader.hasNext()) {
          int event = reader.next();
          if (event == XMLEvent.START_ELEMENT) {
            if ("fullName".equals(reader.getLocalName())) {
              user.setFullName(reader.getElementText());
            }
            boolean inGroup = false;
            if ("groups".equals(reader.getLocalName())) {
              while (reader.hasNext()) {
                int gEvent = reader.next();
                if (gEvent == XMLEvent.START_ELEMENT && "group".equals(reader.getLocalName())) {
                  if (groups.contains(reader.getElementText())) {
                    inGroup = true;
                    break;
                  }
                }
                if (gEvent == XMLEvent.END_ELEMENT && "groups".equals(reader.getLocalName())) {
                  break;
                }
              }
              if (inGroup) {
                users.add(user);
              }
            }
          }
          if (event == XMLEvent.END_ELEMENT && USER_ELEMENT.equals(reader.getLocalName())) {
            break;
          }

        }
      }
    }
    users.sort(Comparator.comparing(User::getFullName).thenComparing(User::getEmail));
    return users;
  }

  public static List<User> getUsersByProjectJAXB(String project) throws Exception {
    if (project == null) {
      return null;
    }
    final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);
    Payload payload = JAXB_PARSER.unmarshal(
        Resources.getResource("payload.xml").openStream());
    String projectId = null;
    for (ProjectType projectType : payload.getProjects().getProject()) {
      if (project.equals(projectType.getValue())) {
        projectId = projectType.getId();
        break;
      }
    }
    if (projectId == null) {
      return null;
    }
    final String finalId = projectId;
    Set<GroupType> groups = payload.getGroups().getGroup().stream()
        .filter(g -> finalId.equals(((ProjectType) g.getProject()).getId())).collect(Collectors
            .toSet());
    if (groups.isEmpty()) {
      return null;
    }
    return payload.getUsers().getUser().stream().filter(u -> {
      if (u.getGroups() == null) {
        return false;
      }
      for (JAXBElement<Object> object : u.getGroups().getGroup()
          ) {
        if (groups.contains((GroupType) object.getValue())) {
          return true;
        }
      }
      return false;
    }).sorted(Comparator.comparing(User::getFullName).thenComparing(User::getEmail))
        .collect(Collectors.toList());
  }

  public static void usersHtml() throws IOException, TransformerException {
    try (InputStream xslInputStream = Resources.getResource("users.xsl").openStream();
        InputStream xmlInputStream = Resources.getResource("payload.xml").openStream()) {

      XsltProcessor processor = new XsltProcessor(xslInputStream);
      System.out.println(processor.transform(xmlInputStream));
    }
  }

  public static void groupsHtml(String project) throws IOException, TransformerException {
    try (InputStream xslInputStream = Resources.getResource("groups.xsl").openStream();
        InputStream xmlInputStream = Resources.getResource("payload.xml").openStream()) {
      XsltProcessor processor = new XsltProcessor(xslInputStream);
      processor.setParameter("project", project);
      System.out.println(processor.transform(xmlInputStream));
    }
  }

}
