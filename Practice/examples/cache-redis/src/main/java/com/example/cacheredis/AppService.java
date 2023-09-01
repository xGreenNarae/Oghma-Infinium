package com.example.cacheredis;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final ModelRepository modelRepository;

    @Cacheable(value = "models")
    public List<Model> findAll() {
        return modelRepository.findAll();
    }

    public Model save(final String name) {
        return modelRepository.save(Model.builder().name(name).build());
    }
}
