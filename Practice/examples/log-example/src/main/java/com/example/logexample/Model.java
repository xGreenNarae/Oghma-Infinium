package com.example.logexample;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Model {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String phoneNumber;

  @Builder
  public Model(String name, String phoneNumber) {
    this.name = name;
    this.phoneNumber = phoneNumber;
  }


}
