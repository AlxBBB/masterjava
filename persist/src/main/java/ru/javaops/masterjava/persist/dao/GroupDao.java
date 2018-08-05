package ru.javaops.masterjava.persist.dao;

import com.bertoncelj.jdbi.entitymapper.EntityMapperFactory;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import ru.javaops.masterjava.persist.model.Group;

import java.util.List;

@RegisterMapperFactory(EntityMapperFactory.class)
public abstract class GroupDao implements AbstractDao {

  @SqlUpdate("TRUNCATE groups CASCADE")
  @Override
  public void clean() {
  }

  @SqlUpdate("INSERT INTO groups (name, project_id, type) VALUES (:name, :projectId, CAST(:type as group_type) ) ON CONFLICT DO NOTHING")
  @GetGeneratedKeys
  public abstract int insert(@BindBean Group group);

  @SqlQuery("SELECT id from groups WHERE name=:it")
  public abstract Group get(@Bind String find);

  @SqlQuery("SELECT * FROM groups ORDER BY name")
  public abstract List<Group> getAll();

}
