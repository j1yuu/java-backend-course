package kkashin.dev.modulehw.controller;

import jakarta.validation.Valid;
import kkashin.dev.modulehw.dto.petDtos.CreatePetDto;
import kkashin.dev.modulehw.dto.petDtos.PetDto;
import kkashin.dev.modulehw.dto.petDtos.UpdatePetDto;
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

    @PutMapping
    public ResponseEntity<PetDto> updatePet(@RequestBody @Valid UpdatePetDto updatePetDto) {
        logger.info("Update request for updatePet");

        var pet = petService.update(updatePetDto);

        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> findById(@PathVariable @Valid Long id) {
        logger.info("Get request for findById pet");

        var pet = petService.findbyId(id);

        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable @Valid Long id) {
        logger.info("Delete request for deletePet");

        petService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
