package com.example.api;


import com.example.core.User;
import com.example.core.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final UserRepository userRepository;

  @GetMapping("/user")
  public List<User> getUser() {
    return userRepository.findAll();
  }

  @PostMapping("/user")
  public String postUser() {
    User user = User.builder()
      .name("John Doe")
      .build();

    userRepository.save(user);

    return "Success";
  }
}
