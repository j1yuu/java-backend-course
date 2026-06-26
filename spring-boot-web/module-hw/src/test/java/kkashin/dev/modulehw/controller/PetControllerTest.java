package kkashin.dev.modulehw.controller;

import kkashin.dev.modulehw.dto.petDtos.CreatePetDto;
import kkashin.dev.modulehw.dto.userDtos.CreateUserDto;
import kkashin.dev.modulehw.dto.petDtos.PetDto;
import kkashin.dev.modulehw.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class PetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createPet_shouldCreateSuccessfully() throws Exception {
        var user = userService.save(new CreateUserDto("jake", "email@mail.ru", 18));

        String stringCreatePetDto = objectMapper.writeValueAsString(new CreatePetDto("Pet", user.id()));

        var createdPetString = mockMvc.perform(
                post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringCreatePetDto)
        )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdPet = objectMapper.readValue(createdPetString, PetDto.class);

        assertEquals(0L, createdPet.id());
        assertEquals(user.id(), createdPet.userId());
        assertEquals("Pet", createdPet.name());
    }

    @Test
    void createPet_shouldThrowNotFoundWhenNoUser() throws Exception {
        String stringCreatePetDto = objectMapper.writeValueAsString(new CreatePetDto("Pet", 0L));

        mockMvc.perform(
                        post("/pet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreatePetDto)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void createPet_shouldFailWhenInvalidName() throws Exception {
        var user = userService.save(new CreateUserDto("jake", "email@mail.ru", 18));

        String stringCreatePetWithNullName = objectMapper.writeValueAsString(new CreatePetDto(null, user.id()));

        mockMvc.perform(
                        post("/pet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreatePetWithNullName)
                )
                .andExpect(status().isBadRequest());


        String stringCreatePetWithLessName = objectMapper.writeValueAsString(new CreatePetDto("P", user.id()));

        mockMvc.perform(
                post("/pet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringCreatePetWithLessName)
                )
                .andExpect(status().isBadRequest());

        String stringCreatePetWithAboveName = objectMapper.writeValueAsString(new CreatePetDto(
                "nameNameNameNameNameNameNameNameName1",
                user.id()
        ));

        mockMvc.perform(
                        post("/pet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreatePetWithAboveName)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createPet_shouldFailWhenInvalidUserId() throws Exception {
        userService.save(new CreateUserDto("jake", "email@mail.ru", 18));

        String stringCreatePetWithNullUserId = objectMapper.writeValueAsString(new CreatePetDto("Pet", null));

        mockMvc.perform(
                        post("/pet")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreatePetWithNullUserId)
                )
                .andExpect(status().isBadRequest());
    }
}
