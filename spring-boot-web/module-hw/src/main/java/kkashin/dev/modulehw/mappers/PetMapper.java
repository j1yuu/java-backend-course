package kkashin.dev.modulehw.mappers;

import kkashin.dev.modulehw.dto.petDtos.CreatePetDto;
import kkashin.dev.modulehw.dto.petDtos.PetDto;
import kkashin.dev.modulehw.dto.petDtos.UpdatePetDto;
import kkashin.dev.modulehw.model.Pet;

public class PetMapper {

    public static PetDto mapPetToPetDto(Pet pet) {
        return new PetDto(
                pet.getId(),
                pet.getName(),
                pet.getUserId()
        );
    }

    public static Pet mapCreateDtoToPet(CreatePetDto createPetDto) {
        return new Pet(
                null,
                createPetDto.name(),
                createPetDto.userId()
        );
    }

    public static Pet mapUpdateDtoOrCurrentToPet(UpdatePetDto updatePetDto, Pet pet) {
        Long id = updatePetDto.id();
        String name = updatePetDto.name() == null ? pet.getName() : updatePetDto.name();
        Long userId = updatePetDto.userId() == null ? pet.getUserId() : updatePetDto.userId();

        return new Pet(
                id,
                name,
                userId
        );
    }
}
