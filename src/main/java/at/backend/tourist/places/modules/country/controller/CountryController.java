package at.backend.tourist.places.modules.country.controller;

import at.backend.tourist.places.modules.country.controller.annotation.*;
import at.backend.tourist.places.modules.country.dtos.CountryDTO;
import at.backend.tourist.places.modules.country.dtos.CountryInsertDTO;
import at.backend.tourist.places.core.shared.Enum.Continent;
import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.country.service.CountryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/countries")
@Tag(name = "Countries", description = "Endpoints for managing countries and their geographical information")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{id}")
    @GetCountryByIdOperation
    public ResponseWrapper<CountryDTO> getCountryById(
            @Parameter(description = "Unique identifier of the country", example = "1", required = true)
            @PathVariable Long id) {
        CountryDTO country = countryService.getById(id);
        return ResponseWrapper.found(country, "country");
    }

    @GetMapping("/name/{name}")
    @GetCountryByNameOperation
    public ResponseWrapper<CountryDTO> getCountryByName(
            @Parameter(description = "Name of the country", example = "Japan", required = true)
            @Valid @PathVariable String name) {
        CountryDTO country = countryService.getByName(name);
        return ResponseWrapper.found(country, "country");
    }

    @GetMapping("/by-continent/{continentSTR}")
    @GetCountriesByContinentOperation
    public ResponseWrapper<List<CountryDTO>> getCountryByContinent(
            @Parameter(description = "Continent name (AFRICA, ANTARCTICA, ASIA, EUROPE, NORTH_AMERICA, OCEANIA, SOUTH_AMERICA)", example = "ASIA", required = true)
            @Valid @PathVariable String continentSTR) {
        Continent continent = Continent.valueOf(continentSTR.toUpperCase());
        List<CountryDTO> countries = countryService.getByContinent(continent);
        return ResponseWrapper.found(countries, "Countries");
    }

    @GetMapping
    @GetAllCountriesOperation
    public ResponseWrapper<List<CountryDTO>> getAllCountries() {
        List<CountryDTO> countries = countryService.getAll();
        return ResponseWrapper.found(countries, "Countries");
    }

    @PostMapping
    @CreateCountryOperation
    public ResponseEntity<ResponseWrapper<CountryDTO>> createCountry(
            @Parameter(description = "Details of the country to create", required = true)
            @Valid @RequestBody CountryInsertDTO insertDTO) {
        CountryDTO createdCountry = countryService.create(insertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(createdCountry, "country"));
    }

    @DeleteMapping("/{id}")
    @DeleteCountryOperation
    public ResponseEntity<ResponseWrapper<Void>> deleteCountry(
            @Parameter(description = "Unique identifier of the country to delete", example = "1", required = true)
            @PathVariable Long id) {
        countryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseWrapper.deleted("country"));
    }
}
