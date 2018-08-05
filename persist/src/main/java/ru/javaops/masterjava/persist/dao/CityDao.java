package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.City;
import ru.javaops.masterjava.persist.model.User;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class CityDao implements AbstractDao {

  @SqlUpdate("TRUNCATE cities CASCADE")
  @Override
  public void clean() {
  }

  @SqlUpdate("INSERT INTO cities (full_name, key) VALUES (:fullName, :key) ON CONFLICT DO NOTHING")
  @GetGeneratedKeys
  public abstract int insert(@BindBean City city );

  @SqlQuery("SELECT * from cities WHERE key=:it")
  public abstract City get(@Bind String find);

  @SqlQuery("SELECT * FROM cities ORDER BY full_name")
  public abstract List<City> getAll();

}
