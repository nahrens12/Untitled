package org.example.mapper;

import org.example.dto.DogDto;
import org.example.entity.Dog;
import org.example.entity.Kenneling;
import org.example.entity.LeavingReason;
import org.example.entity.Suppliers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DogMapperFallbackConfig {

    @Bean
    @ConditionalOnMissingBean(DogMapper.class)
    public DogMapper dogMapperFallback() {
        return new DogMapper() {
            @Override
            public DogDto toDto(Dog dog) {
                if (dog == null) return null;
                DogDto dto = new DogDto();
                dto.id = dog.getBadgeId();
                dto.name = dog.getName();
                dto.breed = dog.getBreed();
                dto.supplier = dog.getSupplier() != null ? dog.getSupplier().name() : null;
                dto.badgeId = dog.getBadgeId() != null ? String.valueOf(dog.getBadgeId()) : null;
                dto.gender = dog.getGender();
                dto.birthDate = dog.getBirthDate();
                dto.dateAcquired = dog.getDateAcquired();
                dto.currentStatus = dog.getCurrentStatus();
                dto.leavingDate = dog.getLeavingDate();
                dto.leavingReason = dog.getLeavingReason();
                dto.kennellingCharacteristic = dog.getKennellingCharacteristic() != null ? dog.getKennellingCharacteristic().name() : null;
                return dto;
            }

            @Override
            public Dog toEntity(DogDto dto) {
                if (dto == null) return null;
                Dog dog = new Dog();
                // Map badge id (primary key)
                if (dto.id != null) {
                    dog.setBadgeId(dto.id);
                } else if (dto.badgeId != null) {
                    try {
                        dog.setBadgeId(Long.parseLong(dto.badgeId));
                    } catch (NumberFormatException ignored) {
                    }
                }

                dog.setName(dto.name);
                dog.setBreed(dto.breed);

                if (dto.supplier != null) {
                    try {
                        dog.setSupplier(Suppliers.valueOf(dto.supplier));
                    } catch (IllegalArgumentException ignored) {
                    }
                }

                dog.setGender(dto.gender);
                dog.setBirthDate(dto.birthDate);
                dog.setDateAcquired(dto.dateAcquired);
                dog.setCurrentStatus(dto.currentStatus);
                dog.setLeavingDate(dto.leavingDate);

                if (dto.leavingReason != null) {
                    try {
                        dog.setLeavingReason(dto.leavingReason);
                    } catch (Exception ignored) {
                    }
                }

                if (dto.kennellingCharacteristic != null) {
                    try {
                        dog.setKennellingCharacteristic(Kenneling.valueOf(dto.kennellingCharacteristic));
                    } catch (Exception ignored) {
                    }
                }

                return dog;
            }
        };
    }
}
