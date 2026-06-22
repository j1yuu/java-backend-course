package kkashin.dev.modulehw.service;

import kkashin.dev.modulehw.dto.CreatePetDto;
import kkashin.dev.modulehw.dto.PetDto;
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
        if (userRepository.findById(createPetDto.userId()).isEmpty())
            throw new NoSuchElementException("User with given id was not found: %s".formatted(createPetDto.userId()));

        var pet = petRepository.save(createPetDto);

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
