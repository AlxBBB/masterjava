package ru.javaops.masterjava.persist.model;

import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class City extends BaseEntity{

  @Column("full_name")
  @NonNull
  private String fullName;
  @Nonnull
  private  String key;

  public City(Integer id, String fullName, String key) {
    this(fullName, key);
    this.id = id;
  }
}
