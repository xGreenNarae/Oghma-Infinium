package com.example.entitydto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EntityToDtoTest {
    @Autowired
    private ModelMapper mm;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("ModelMapper Test")
    void modelMapperTest() throws JsonProcessingException {
        userRepository.save(User.builder()
            .name("test")
            .email("test@gmail.com")
            .build()
        );

        User user = userRepository.findByEmail("test@gmail.com");


        UserNameDto userNameDto = mm.map(user, UserNameDto.class);

        System.out.println(om.writeValueAsString(userNameDto));

        assertThat(userNameDto.getName()).isEqualTo(user.getName());
    }
}