package ru.javaops.masterjava.persist;

import java.util.ArrayList;
import java.util.List;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.City;

public class CityTestData {
  public static City moscow;
  public static int moscowId;
  public static City kiev;
  public static City minsk;
  public static List<City> someCities=new ArrayList<>();



  public static void init() {
    moscow = new City("Москва","mow");
    kiev=new City ("Киев","kiv");
    minsk=new City ("Минск","mnsk");
  }

  public static void setUp() {
    CityDao dao = DBIProvider.getDao(CityDao.class);
    dao.clean();
    someCities.clear();
    DBIProvider.getDBI().useTransaction((conn, status) -> {
      minsk.setId(dao.insert(minsk));
      someCities.add(minsk);
      moscowId=dao.insert(moscow);
      moscow.setId(moscowId);
      someCities.add(moscow);
    });
  }
}
