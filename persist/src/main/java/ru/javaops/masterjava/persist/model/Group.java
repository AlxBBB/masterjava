package ru.javaops.masterjava.persist.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Group {
   @NonNull
   public String name;
   @NonNull
   public GroupType type;
   @NonNull
   public  int projectId;
}
