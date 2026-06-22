package kkashin.dev.modulehw.service;

import kkashin.dev.modulehw.dto.CreateUserDto;
import kkashin.dev.modulehw.dto.UpdateUserDto;
import kkashin.dev.modulehw.dto.UserDto;
import kkashin.dev.modulehw.mappers.UserMapper;
import kkashin.dev.modulehw.repository.PetRepository;
import kkashin.dev.modulehw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    public UserService(
            UserRepository userRepository,
            PetRepository petRepository
    ) {
        this.userRepository = userRepository;
        this.petRepository = petRepository;
    }

    public UserDto save(CreateUserDto createUserDto) {
        var user = userRepository.save(createUserDto);

        return UserMapper.mapUserAndPetsToUserDto(user, new ArrayList<>());
    }

    public UserDto update(UpdateUserDto updateUserDto) {
        var currentUserOptional = userRepository.findById(updateUserDto.id());

        if (currentUserOptional.isEmpty())
            throw new NoSuchElementException("User with given id was not found: %s".formatted(updateUserDto.id()));

        var currentUser = currentUserOptional.get();
        var userToUpdate = UserMapper.mapUpdateDtoOrCurrentToUser(updateUserDto, currentUser);

        var updatedUser = userRepository.update(userToUpdate);
        var updatedUserPets = petRepository.findPetsByUserId(updatedUser.getId());

        return UserMapper.mapUserAndPetsToUserDto(updatedUser, updatedUserPets);
    }

    public void delete(Long id) {
        var currentUser = userRepository.findById(id);

        if (currentUser.isEmpty())
            throw new NoSuchElementException("User with given id was not found: %s".formatted(id));

        userRepository.delete(id);
        petRepository.deletePetsByUserId(id);
    }

    public UserDto findById(Long id) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isEmpty())
            throw new NoSuchElementException("User with given id was not found: %s".formatted(id));

        var userPets = petRepository.findPetsByUserId(id);
        return UserMapper.mapUserAndPetsToUserDto(userOptional.get(), userPets);
    }
}
