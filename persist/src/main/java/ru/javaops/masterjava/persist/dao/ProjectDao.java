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
import ru.javaops.masterjava.persist.model.Project;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class ProjectDao implements AbstractDao {

  @SqlUpdate("TRUNCATE projects CASCADE")
  @Override
  public void clean() {
  }

  @SqlUpdate("INSERT INTO projects (name, description) VALUES (:name, :description) ON CONFLICT DO NOTHING")
  @GetGeneratedKeys
  public abstract int insert(@BindBean Project project);

  @SqlQuery("SELECT id from projects WHERE name=:it")
  public abstract Project get(@Bind String find);

  @SqlQuery("SELECT * FROM projects ORDER BY name")
  public abstract List<Project> getAll();

}
