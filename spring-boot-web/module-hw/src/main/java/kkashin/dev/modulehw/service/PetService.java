package kkashin.dev.modulehw.service;

import kkashin.dev.modulehw.dto.petDtos.CreatePetDto;
import kkashin.dev.modulehw.dto.petDtos.PetDto;
import kkashin.dev.modulehw.dto.petDtos.UpdatePetDto;
import kkashin.dev.modulehw.mappers.PetMapper;
import kkashin.dev.modulehw.repository.PetRepository;
import kkashin.dev.modulehw.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public PetService(
            PetRepository petRepository,
            UserRepository userRepository
    ) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
    }

    public PetDto save(CreatePetDto createPetDto) {
        userRepository.findById(createPetDto.userId()).orElseThrow(
                () -> new NoSuchElementException("User with given id was not found: %s".formatted(createPetDto.userId()))
        );

        var pet = petRepository.save(createPetDto);

        return PetMapper.mapPetToPetDto(pet);
    }

    public PetDto update(UpdatePetDto updatePetDto) {
        var currentPet = petRepository.findById(updatePetDto.id()).orElseThrow(
                () -> new NoSuchElementException("Pet with given id was not found: %s".formatted(updatePetDto.id()))
        );

        var updatablePet = PetMapper.mapUpdateDtoOrCurrentToPet(updatePetDto, currentPet);
        userRepository.findById(updatablePet.getUserId()).orElseThrow(
                () -> new NoSuchElementException("User with given id was not found: %s".formatted(updatablePet.getUserId()))
        );

        var updatedPet = petRepository.update(updatablePet);
        return PetMapper.mapPetToPetDto(updatedPet);
    }

    public PetDto findbyId(Long id) {
        var pet = petRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Pet with given id was not found: %s".formatted(id))
        );

        return PetMapper.mapPetToPetDto(pet);
    }

    public void delete(Long id) {
        var petOptional = petRepository.findById(id);

        if (petOptional.isEmpty())
            throw new NoSuchElementException("Pet with given id was not found: %s".formatted(id));

        var pet = petOptional.get();

        if (userRepository.findById(pet.getUserId()).isEmpty())
            throw new NoSuchElementException("User with given id was not found: %s".formatted(pet.getUserId()));

        petRepository.delete(pet.getId());
    }
}
