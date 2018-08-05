package ru.javaops.masterjava.persist.model;


import com.bertoncelj.jdbi.entitymapper.Column;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Group extends BaseEntity {
   @NonNull
   public String name;
   @NonNull
   public GroupType type;
   @NonNull
   @Column("project_id")
   public  int projectId;

   public Group(Integer id, String name, GroupType type, int projectId) {
      this(name, type, projectId);
      this.id=id;
   }
}
