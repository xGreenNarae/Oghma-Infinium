package com.example.logexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class Controller {

  private final ModelRepository modelRepository;

  @GetMapping("/error")
  public String error() {
    log.error("error");
    return "error";
  }

  @PostMapping("/model")
  public String model() {
    log.info("model created");
    modelRepository.save(Model.builder()
      .name("model")
      .phoneNumber("010-1234-5678")
      .build());
    return "model created";
  }

  @GetMapping("/model")
  public List<Model> modelGet() {
    log.info("model get");
    return modelRepository.findAll();
  }

  @GetMapping("/badAccess")
  public String badAccess() {
    log.warn("bad access {} ", "Value"); // value를 넣을때는 {}를 사용한다.
    return "bad access";
  }
}
