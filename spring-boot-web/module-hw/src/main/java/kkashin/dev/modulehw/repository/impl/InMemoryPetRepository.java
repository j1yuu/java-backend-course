package kkashin.dev.modulehw.repository.impl;

import kkashin.dev.modulehw.dto.CreatePetDto;
import kkashin.dev.modulehw.mappers.PetMapper;
import kkashin.dev.modulehw.model.Pet;
import kkashin.dev.modulehw.repository.PetRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPetRepository implements PetRepository {
    private final Map<Long, Pet> petMap;
    private final AtomicLong idCounter = new AtomicLong(0);

    public InMemoryPetRepository() {
        this.petMap = new HashMap<>();
    }

    @Override
    public Pet save(CreatePetDto createPetDto) {
        var pet = PetMapper.mapCreateDtoToPet(createPetDto);
        var id = idCounter.getAndIncrement();

        pet.setId(id);
        petMap.put(id, pet);

        return pet;
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(petMap.get(id));
    }

    @Override
    public List<Pet> findPetsByUserId(Long id) {
        return petMap.values().stream()
                .filter(p -> Objects.equals(p.getUserId(), id))
                .toList();
    }

    @Override
    public void delete(Long id) {
        petMap.remove(id);
    }

    @Override
    public void deletePetsByUserId(Long id) {
        var petsToDelete = petMap.values().stream().filter(p -> Objects.equals(p.getUserId(), id)).toList();

        for (var pet : petsToDelete) {
            petMap.remove(pet.getId());
        }
    }
}
