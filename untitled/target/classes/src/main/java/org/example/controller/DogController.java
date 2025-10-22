package org.example.controller;

import org.example.dto.DogDto;
import org.example.entity.*;
import org.example.mapper.DogMapper;
import org.example.service.DogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dogs")
public class DogController {
    private final DogService service;
    private final DogMapper mapper;

    public DogController(DogService service, DogMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping("/")
    public DogDto create(@RequestBody DogDto dto) {
        Dog dog = mapper.toEntity(dto);
        return mapper.toDto(service.save(dog));
    }

    @GetMapping("/")
    public List<DogDto> list(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String breed,
        @RequestParam(required = false) String supplier,
        @RequestParam(required = false) Long badgeId,

        @RequestParam(defaultValue = "100") int size
    ) {
        if (name != null || breed != null || supplier != null) {
            List<Dog> dogs = service.findDogsByAttributes();
            return dogs.stream().map(mapper::toDto).toList();
        } else {
            List<Dog> dogs = service.searchForDog(name, badgeId);
            return dogs.stream().limit(size).map(mapper::toDto).toList();
        }
    }


    @PutMapping("/{id}")
    public DogDto update(
            @PathVariable String value,
            @RequestBody DogDto dto
    ) {
        Dog dog = service.findDogByAttribute(value).orElseThrow();
        return mapper.toDto(service.save(dog));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.dogDeletion(id);
    }

    // Enum endpoints
    @GetMapping("/statuses")
    public Status[] statuses() {
        return Status.values();
    }

    @GetMapping("/leaving-reasons")
    public LeavingReason[] leavingReasons() {
        return LeavingReason.values();
    }
}
