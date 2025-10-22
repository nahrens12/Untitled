package service;

import org.example.entity.Dog;
import org.example.repository.DogRepository;
import org.example.service.DogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class DogServiceTest {

    @Mock
    private DogRepository dogRepository;

    @InjectMocks
    private DogService dogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldCallRepositorySave() {
        Dog dog = new Dog();
        when(dogRepository.save(dog)).thenReturn(dog);

        Dog result = dogService.save(dog);
        assertThat(result).isEqualTo(dog);
    }

    @Test
    void findById_shouldReturnDogIfNotDeleted() {
        Dog dog = new Dog();
        dog.setDeleted(false);
        var id = 1L;
        when(dogRepository.findById(id)).thenReturn(Optional.of(dog));

        Optional<Dog> result = dogService.findById(id);

        assertThat(result).contains(dog);
    }


    @Test
    void dogDeletion_shouldSetDeletedTrue() {
        Dog dog = new Dog();
        dog.setDeleted(false);
        var id = 1L;
        when(dogRepository.findById(id)).thenReturn(Optional.of(dog));

        dogService.dogDeletion(id);

        assertThat(dog.isDeleted()).isTrue();
        verify(dogRepository).save(dog);
    }
}