package org.example;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.example.TestObjectChildComplete;

@Getter
@EqualsAndHashCode
public class TestObjectNestedComplete {
  private int id;
  private String name;
  private TestObjectChildComplete child;

  @Builder
  public TestObjectNestedComplete(int id, String name, TestObjectChildComplete child) {
      this.id = id;
      this.name = name;
      this.child = child;
  }
}
