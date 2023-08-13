package org.example;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TestObjectPureInComplete {
  private int id;
  private String name;

  @Builder
  public TestObjectPureInComplete(int id, String name) {
    this.id = id;
    this.name = name;
  }
}
