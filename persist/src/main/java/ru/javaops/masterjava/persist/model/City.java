package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class City extends BaseEntity{

  @Column("full_name")
  @NonNull
  private String fullName;

  public City(Integer id, String fullName) {
    this(fullName);
    this.id = id;
  }
}
