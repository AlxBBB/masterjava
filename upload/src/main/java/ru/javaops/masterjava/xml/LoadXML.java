package ru.javaops.masterjava.xml;

import com.google.common.io.Resources;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.stream.events.XMLEvent;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

public class LoadXML {
  private static final Comparator<User> USER_COMPARATOR = Comparator.comparing(User::getValue).thenComparing(User::getEmail);
  static Set<User> users = new TreeSet<>(USER_COMPARATOR);

  static public void load(String inFile) throws Exception {
    Path path= Paths.get(inFile);

    try (InputStream is = Files.newInputStream(path) ) {
      users.clear();
      StaxStreamProcessor processor = new StaxStreamProcessor(is);
      JaxbParser parser = JaxbParser.get(User.class);
      while (processor.doUntil(XMLEvent.START_ELEMENT, "User")) {
        User user = parser.unmarshal(processor.getReader(), User.class);
        users.add(user);
      }
    }
  }

  public static Set<User> getUsers() {
    return users;
  }
}
