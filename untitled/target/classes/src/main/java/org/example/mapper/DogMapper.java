package org.example.mapper;

import org.example.dto.DogDto;
import org.example.entity.Dog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DogMapper {

    @Mapping(source = "badgeId", target = "id")
    @Mapping(target = "badgeId", expression = "java(dog.getBadgeId() != null ? String.valueOf(dog.getBadgeId()) : null)")
    @Mapping(target = "supplier", expression = "java(dog.getSupplier() != null ? dog.getSupplier().name() : null)")
    @Mapping(target = "kennellingCharacteristic", expression = "java(dog.getKennellingCharacteristic() != null ? dog.getKennellingCharacteristic().name() : null)")
    DogDto toDto(Dog dog);

    @Mapping(target = "badgeId", expression = "java(dto.id != null ? dto.id : (dto.badgeId != null ? Long.parseLong(dto.badgeId) : null))")
    @Mapping(target = "supplier", expression = "java(dto.supplier != null ? org.example.entity.Suppliers.valueOf(dto.supplier) : null)")
    @Mapping(target = "kennellingCharacteristic", expression = "java(dto.kennellingCharacteristic != null ? org.example.entity.Kenneling.valueOf(dto.kennellingCharacteristic) : null)")
    Dog toEntity(DogDto dto);
}
