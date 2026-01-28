package at.backend.tourist.places.modules.country.auto_mappers;

import at.backend.tourist.places.modules.country.dtos.CountryDTO;
import at.backend.tourist.places.modules.country.dtos.CountryInsertDTO;
import at.backend.tourist.places.modules.country.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDTO entityToDTO(Country country);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "places", ignore = true)
    Country DTOToEntity(CountryInsertDTO insertDTO);
    
    @Mapping(target = "places", ignore = true)
    Country DTOToEntity(CountryDTO countryDTO);
}