package org.example;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TestObjectNestedInComplete {
  private int id;
  private String name;
  private TestObjectChildInComplete child;

  @Builder
  public TestObjectNestedInComplete(int id, String name, TestObjectChildInComplete child) {
      this.id = id;
      this.name = name;
      this.child = child;
  }
}
