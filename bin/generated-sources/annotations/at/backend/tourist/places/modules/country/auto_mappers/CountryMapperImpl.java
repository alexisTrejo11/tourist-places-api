package at.backend.tourist.places.modules.country.auto_mappers;

import at.backend.tourist.places.modules.country.dtos.CountryDTO;
import at.backend.tourist.places.modules.country.dtos.CountryInsertDTO;
import at.backend.tourist.places.modules.country.model.Country;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T11:43:17-0600",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.45.0.v20260224-0835, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public CountryDTO entityToDTO(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDTO countryDTO = new CountryDTO();

        countryDTO.setArea( country.getArea() );
        countryDTO.setCapital( country.getCapital() );
        countryDTO.setContinent( country.getContinent() );
        countryDTO.setCurrency( country.getCurrency() );
        countryDTO.setFlagImage( country.getFlagImage() );
        countryDTO.setId( country.getId() );
        countryDTO.setLanguage( country.getLanguage() );
        countryDTO.setName( country.getName() );
        countryDTO.setPopulation( country.getPopulation() );

        return countryDTO;
    }

    @Override
    public Country DTOToEntity(CountryInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        Country country = new Country();

        country.setArea( insertDTO.getArea() );
        country.setCapital( insertDTO.getCapital() );
        country.setContinent( insertDTO.getContinent() );
        country.setCurrency( insertDTO.getCurrency() );
        country.setFlagImage( insertDTO.getFlagImage() );
        country.setLanguage( insertDTO.getLanguage() );
        country.setName( insertDTO.getName() );
        country.setPopulation( insertDTO.getPopulation() );

        return country;
    }

    @Override
    public Country DTOToEntity(CountryDTO countryDTO) {
        if ( countryDTO == null ) {
            return null;
        }

        Country country = new Country();

        country.setArea( countryDTO.getArea() );
        country.setCapital( countryDTO.getCapital() );
        country.setContinent( countryDTO.getContinent() );
        country.setCurrency( countryDTO.getCurrency() );
        country.setFlagImage( countryDTO.getFlagImage() );
        country.setId( countryDTO.getId() );
        country.setLanguage( countryDTO.getLanguage() );
        country.setName( countryDTO.getName() );
        country.setPopulation( countryDTO.getPopulation() );

        return country;
    }
}
