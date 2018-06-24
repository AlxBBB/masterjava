package ru.javaops.masterjava.persist.service;

import java.util.List;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;

public class UserServiceImpl implements UserService {

  @Override
  public void clean() {
    DBIProvider.getDao(UserDao.class).clean();
  }

  @Override
  public int[] save(List<User> users) {
     return DBIProvider.getDao(UserDao.class).insert(users);
  }

  @Override
  public List<User> getList() {
    return DBIProvider.getDao(UserDao.class).get();
  }

  @Override
  public List<User> getList(int top) {
    return DBIProvider.getDao(UserDao.class).getWithLimit(top);
  }
}
