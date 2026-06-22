package kkashin.dev.modulehw.repository;

import kkashin.dev.modulehw.dto.CreatePetDto;
import kkashin.dev.modulehw.model.Pet;

import java.util.List;
import java.util.Optional;

public interface PetRepository {
    Pet save(CreatePetDto createPetDto);
    Optional<Pet> findById(Long id);
    List<Pet> findPetsByUserId(Long id);
    void delete(Long id);
}
