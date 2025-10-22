//package repository;
//
//import org.example.entity.Dog;
//import org.example.repository.DogRepository;
//import org.example.service.DogService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class DogRepositoryTest {
//
//    @Mock
//    private DogRepository dogRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void findAllDogs() {
//        Dog dog = new Dog();
//        dog.setName("Rex");
//        List<Dog>dogList = java.util.List.of(dog);
//        when(dogRepository.findAllByDeletedFalse()).thenReturn(dogList);
//        when(dogRepository.search("rex", 1L)).thenReturn(dogList);
////        verify(dogRepository).findAll();
////        when(dogRepository.findAll()).thenReturn().thenReturn(java.util.List.of(dog));
//
//    }
//
//    @Test
//    void findAllDogsInDatabase() {
//        Dog dog = new Dog();
//        dog.setName("Rex");
//        List<Dog> dogList = java.util.List.of(dog);
//        when(dogRepository.findAll()).thenReturn(dogList);
//
////        verify(dogRepository).findAll();
////        when(dogRepository.findAll()).thenReturn().thenReturn(java.util.List.of(dog));
//
//    }
//
//
//
//    @Test
//    void softDeleteAndFind() {
//        Dog dog = new Dog();
//        dog.setName("Max");
//        dog = dogRepository.save(dog);
//
//        dog.setDeleted(true);
//        dogRepository.save(dog);
//
//        Optional<Dog> found = dogRepository.findById(dog.getBadgeId());
//        assertThat(found).isPresent();
//        assertThat(found.get().isDeleted()).isTrue();
//    }
//}
