package org.example.repository;

import org.example.entity.Dog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findByName(String name);

    // find by badge id (badgeId is the entity id field)
    Optional<Dog> findByBadgeId(Long badgeId);

    // list non-deleted dogs (pageable)
    Page<Dog> findAllByDeletedFalse(Pageable pageable);

    // non-pageable variant
    List<Dog> findAllByDeletedFalse();

    @org.springframework.data.jpa.repository.Query("SELECT d FROM Dog d WHERE d.deleted = false AND (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND (:badgeId IS NULL OR d.badgeId = :badgeId)")
    List<Dog> search(@org.springframework.data.repository.query.Param("name") String name, @org.springframework.data.repository.query.Param("badgeId") Long badgeId);
}