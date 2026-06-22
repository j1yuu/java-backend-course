package kkashin.dev.modulehw.controller;

import jakarta.validation.Valid;
import kkashin.dev.modulehw.dto.CreatePetDto;
import kkashin.dev.modulehw.dto.PetDto;
import kkashin.dev.modulehw.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")
public class PetController {
    private final Logger logger = LoggerFactory.getLogger(PetController.class);
    private final PetService petService;

    public PetController(
            PetService petService
    ) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid CreatePetDto createPetDto) {
        logger.info("Post request for createPet");

        var pet = petService.save(createPetDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        logger.info("Delete request for deletePet");

        petService.delete(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
