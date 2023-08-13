package org.example;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TestObjectChildInComplete {
  private int id;
  private String name;

  @Builder
  public TestObjectChildInComplete(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
