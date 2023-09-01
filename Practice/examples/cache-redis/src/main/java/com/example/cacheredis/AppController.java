package com.example.cacheredis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AppController {
    private final AppService appService;

    @GetMapping("/")
    public ResponseEntity<List<Model>> findAll() {
        return ResponseEntity.ok().body(appService.findAll());
    }

    @PostMapping("/{name}")
    public ResponseEntity<Model> save(@PathVariable final String name) {
        return ResponseEntity.ok().body(appService.save(name));
    }

}
