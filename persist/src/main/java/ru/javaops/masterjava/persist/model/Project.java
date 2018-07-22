package ru.javaops.masterjava.persist.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Project extends BaseEntity {
  @NonNull
  public String name;
  public String description;

  public Project(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Project(Integer id, String name,String description) {
    this(name,description);
    this.id = id;
  }
}
