package ru.javaops.masterjava.persist.service;

import java.util.List;
import ru.javaops.masterjava.persist.model.User;

public interface UserService {

  void clean();

  int[] save(List<User> users);

  List<User> getList();

  List<User> getList(int top);
}
