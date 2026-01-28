package at.backend.tourist.places.modules.country.service;

import at.backend.tourist.places.core.exceptions.ResourceAlreadyExistsException;
import at.backend.tourist.places.core.exceptions.ResourceNotFoundException;
import at.backend.tourist.places.modules.country.auto_mappers.CountryMapper;
import at.backend.tourist.places.modules.country.dtos.CountryDTO;
import at.backend.tourist.places.modules.country.dtos.CountryInsertDTO;
import at.backend.tourist.places.core.shared.Enum.Continent;
import at.backend.tourist.places.modules.country.model.Country;
import at.backend.tourist.places.modules.country.repository.CountryRepository;
import at.backend.tourist.places.core.shared.StringHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Override
    @Cacheable(value = "countryById", key = "#id")
    public CountryDTO getById(Long id) {
        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("country", "id", id));

        return countryMapper.entityToDTO(country);
    }

    @Override
    @Cacheable(value = "allCountries")
    public List<CountryDTO> getAll() {
        List<Country> countries = countryRepository.findAll();

        return countries.stream()
                .map(countryMapper::entityToDTO)
                .toList();
    }

    @Override
    @Cacheable(value = "countriesByContinent", key = "#continent.name")
    public List<CountryDTO> getByContinent(Continent continent) {
        List<Country> countries = countryRepository.findByContinent(continent);

        return countries.stream()
                .map(countryMapper::entityToDTO)
                .toList();
    }

    @Override
    @Cacheable(value = "countryByName", key = "#name")
    public CountryDTO getByName(String name) {
        name = StringHandler.capitalize(name);

        String finalName = name;
        Country country = countryRepository.findByName(name)
                .orElseThrow(() -> new ResourceAlreadyExistsException("country", "name", finalName));

        return countryMapper.entityToDTO(country);
    }

    private void validate(CountryInsertDTO insertDTO) {
        String name = StringHandler.capitalize(insertDTO.getName());

        countryRepository.findByName(name).ifPresent(country -> {
            throw new ResourceAlreadyExistsException("country", "name", name);
        });
    }

    @Override
    public CountryDTO create(CountryInsertDTO insertDTO) {
        validate(insertDTO);

        Country country = countryMapper.DTOToEntity(insertDTO);
        countryRepository.saveAndFlush(country);

        return countryMapper.entityToDTO(country);
    }

    @Override
    public void delete(Long id) {
        if (!countryRepository.existsById(id)) {
            throw new ResourceNotFoundException("country", "id", id);
        }

        countryRepository.deleteById(id);
    }
}