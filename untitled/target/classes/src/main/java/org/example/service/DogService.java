package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Dog;
import org.example.repository.DogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DogService {

    private final DogRepository repo;

    public Dog save(Dog dog) {
        return repo.save(dog);
    }

    public Optional<Dog> findById(Long id) {
        return repo.findById(id).filter(d -> !d.isDeleted());
    }

    public List<Dog> findDogsByAttributes() {
        return repo.findAllByDeletedFalse();
    }

    public List<Dog> searchForDog(String name, Long badgeId) {
        return repo.search(name, badgeId);
    }

    // Flexible lookup: if value parses to Long, try badgeId, otherwise try name
    public Optional<Dog> findDogByAttribute(String value) {
        if (value == null) return Optional.empty();
        try {
            Long id = Long.parseLong(value);
            return repo.findByBadgeId(id).filter(d -> !d.isDeleted());
        } catch (NumberFormatException e) {
            return repo.findByName(value).filter(d -> !d.isDeleted());
        }
    }

    public void dogDeletion(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        repo.findById(id).ifPresent(dog -> {
            dog.setDeleted(true);
            repo.save(dog);
        });
    }
}
