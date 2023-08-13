package org.example;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TestObjectChildComplete {
  private int id;
  private String name;

  @Builder
  public TestObjectChildComplete(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
