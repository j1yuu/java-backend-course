package kkashin.dev.modulehw.mappers;

import kkashin.dev.modulehw.dto.userDtos.CreateUserDto;
import kkashin.dev.modulehw.dto.petDtos.PetDto;
import kkashin.dev.modulehw.dto.userDtos.UpdateUserDto;
import kkashin.dev.modulehw.dto.userDtos.UserDto;
import kkashin.dev.modulehw.model.Pet;
import kkashin.dev.modulehw.model.User;

import java.util.List;

public class UserMapper {

    public static UserDto mapUserAndPetsToUserDto(User user, List<Pet> pets) {
        List<PetDto> petsDto = pets.stream()
                .map(PetMapper::mapPetToPetDto)
                .toList();

        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAge(),
                petsDto
        );
    }

    public static User mapUpdateDtoOrCurrentToUser(UpdateUserDto updateUserDto, User currentUser) {
        var id = updateUserDto.id();
        var name = updateUserDto.name() == null ? currentUser.getName() : updateUserDto.name();
        var email = updateUserDto.email() == null ? currentUser.getEmail() : updateUserDto.email();
        var age = updateUserDto.age() == null ? currentUser.getAge() : updateUserDto.age();

        return new User(
                id,
                name,
                email,
                age
        );
    }

    public static User mapCreateDtotoUser(CreateUserDto createUserDto) {
        var name = createUserDto.name();
        var email = createUserDto.email();
        var age = createUserDto.age();

        return new User(
                null,
                name,
                email,
                age
        );
    }
}
