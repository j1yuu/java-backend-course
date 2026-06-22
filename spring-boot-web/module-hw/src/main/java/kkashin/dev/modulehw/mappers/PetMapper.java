package kkashin.dev.modulehw.mappers;

import kkashin.dev.modulehw.dto.CreatePetDto;
import kkashin.dev.modulehw.dto.PetDto;
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
}
