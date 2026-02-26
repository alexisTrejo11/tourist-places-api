package at.backend.tourist.places.modules.country.auto_mappers;

import at.backend.tourist.places.modules.country.dtos.CountryDTO;
import at.backend.tourist.places.modules.country.dtos.CountryInsertDTO;
import at.backend.tourist.places.modules.country.model.Country;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T12:37:48-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public CountryDTO entityToDTO(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryDTO countryDTO = new CountryDTO();

        countryDTO.setId( country.getId() );
        countryDTO.setName( country.getName() );
        countryDTO.setCapital( country.getCapital() );
        countryDTO.setCurrency( country.getCurrency() );
        countryDTO.setLanguage( country.getLanguage() );
        countryDTO.setPopulation( country.getPopulation() );
        countryDTO.setArea( country.getArea() );
        countryDTO.setContinent( country.getContinent() );
        countryDTO.setFlagImage( country.getFlagImage() );

        return countryDTO;
    }

    @Override
    public Country DTOToEntity(CountryInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        Country country = new Country();

        country.setName( insertDTO.getName() );
        country.setCapital( insertDTO.getCapital() );
        country.setCurrency( insertDTO.getCurrency() );
        country.setLanguage( insertDTO.getLanguage() );
        country.setPopulation( insertDTO.getPopulation() );
        country.setArea( insertDTO.getArea() );
        country.setContinent( insertDTO.getContinent() );
        country.setFlagImage( insertDTO.getFlagImage() );

        return country;
    }

    @Override
    public Country DTOToEntity(CountryDTO countryDTO) {
        if ( countryDTO == null ) {
            return null;
        }

        Country country = new Country();

        country.setId( countryDTO.getId() );
        country.setName( countryDTO.getName() );
        country.setCapital( countryDTO.getCapital() );
        country.setCurrency( countryDTO.getCurrency() );
        country.setLanguage( countryDTO.getLanguage() );
        country.setPopulation( countryDTO.getPopulation() );
        country.setArea( countryDTO.getArea() );
        country.setContinent( countryDTO.getContinent() );
        country.setFlagImage( countryDTO.getFlagImage() );

        return country;
    }
}
