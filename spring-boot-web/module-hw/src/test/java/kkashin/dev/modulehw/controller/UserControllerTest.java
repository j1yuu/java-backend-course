package kkashin.dev.modulehw.controller;

import kkashin.dev.modulehw.dto.CreateUserDto;
import kkashin.dev.modulehw.dto.UserDto;
import kkashin.dev.modulehw.service.PetService;
import kkashin.dev.modulehw.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void createUser_shouldSuccessfullyCreateWhenOk() throws Exception {
        var createUserDto = new CreateUserDto(
                "Jake",
                "jake@valley.com",
                18
        );

        String stringCreateUserDto = objectMapper.writeValueAsString(createUserDto);

        String createdUserJson = mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringCreateUserDto)
        )
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdUser = objectMapper.readValue(createdUserJson, UserDto.class);

        var expectedUser = new UserDto(
                0L,
                createUserDto.name(),
                createUserDto.email(),
                createUserDto.age(),
                new ArrayList<>()
        );

        assertThat(createdUser).usingRecursiveAssertion().isEqualTo(expectedUser);
    }

    @Test
    void createUser_shouldFailWhenNullName() throws Exception {
        var invalidCreateUserDto = new CreateUserDto(
                null,
                "email@mail.ru",
                32
        );

        String stringCreateUserDto = objectMapper.writeValueAsString(invalidCreateUserDto);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreateUserDto)
                )
                .andExpect(status().is(400));
    }

    @Test
    void createUser_shouldFailWhenNullEmail() throws Exception {
        var invalidCreateUserDto = new CreateUserDto(
                "name",
                null,
                32
        );

        String stringCreateUserDto = objectMapper.writeValueAsString(invalidCreateUserDto);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreateUserDto)
                )
                .andExpect(status().is(400));
    }

    @Test
    void createUser_shouldFailWhenNullAge() throws Exception {
        var invalidCreateUserDto = new CreateUserDto(
                "name",
                "email@mail.ru",
                null
        );

        String stringCreateUserDto = objectMapper.writeValueAsString(invalidCreateUserDto);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreateUserDto)
                )
                .andExpect(status().is(400));
    }

    @Test
    void createUser_shouldFailWhenNameAboveOrLess() throws Exception {
        var nameLess = new CreateUserDto(
                "n",
                "email@mail.ru",
                18
        );

        String stringNameLess = objectMapper.writeValueAsString(nameLess);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringNameLess)
                )
                .andExpect(status().is(400));

        var nameAbove = new CreateUserDto(
                "nameNameNameNameNameNameNameNameName1",
                "email@mail.ru",
                18
        );

        String stringNameAbove = objectMapper.writeValueAsString(nameAbove);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringNameAbove)
                )
                .andExpect(status().is(400));
    }

    @Test
    void createUser_shouldFailWhenEmailIncorrect() throws Exception {
        var incorrect = new CreateUserDto(
                "name",
                "email@mail.",
                18
        );

        String stringIncorrect = objectMapper.writeValueAsString(incorrect);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringIncorrect)
                )
                .andExpect(status().is(400));
    }

    @Test
    void createUser_shouldFailWhenAgeAboveOrLess() throws Exception {
        var less = new CreateUserDto(
                "name",
                "email@mail.ru",
                0
        );

        String stringNameLess = objectMapper.writeValueAsString(less);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringNameLess)
                )
                .andExpect(status().is(400));

        var above = new CreateUserDto(
                "name",
                "email@mail.ru",
                101
        );

        String stringNameAbove = objectMapper.writeValueAsString(above);

        mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringNameAbove)
                )
                .andExpect(status().is(400));
    }

    @Test
    void findById_shouldReturnUserWhenExist() throws Exception {
        var createUserDto = new CreateUserDto(
                "name",
                "email@mail.ru",
                18
        );
        String stringCreateUserDto = objectMapper.writeValueAsString(createUserDto);

        String createdUserJson = mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreateUserDto)
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdUser = objectMapper.readValue(createdUserJson, UserDto.class);

        var foundUserString = mockMvc.perform(
                get("/user/%s".formatted(createdUser.id()))
        )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        var foundUser = objectMapper.readValue(foundUserString, UserDto.class);

        assertThat(foundUser).usingRecursiveAssertion().isEqualTo(createdUser);
    }

    @Test
    void findById_shouldReturnUserWhenNotExist() throws Exception {
        mockMvc.perform(
                        get("/user/%s".formatted(1))
                )
                .andExpect(status().is(404));

        var createUserDto = new CreateUserDto(
                "name",
                "email@mail.ru",
                18
        );
        String stringCreateUserDto = objectMapper.writeValueAsString(createUserDto);

        String createdUserJson = mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stringCreateUserDto)
                )
                .andReturn()
                .getResponse()
                .getContentAsString();

        var createdUser = objectMapper.readValue(createdUserJson, UserDto.class);

        mockMvc.perform(
                        get("/user/%s".formatted(createdUser.id() + 1))
                )
                .andExpect(status().is(404));
    }
}
