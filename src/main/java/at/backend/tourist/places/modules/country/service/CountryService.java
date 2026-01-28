package at.backend.tourist.places.modules.country.service;

import at.backend.tourist.places.modules.country.dtos.CountryDTO;
import at.backend.tourist.places.modules.country.dtos.CountryInsertDTO;
import at.backend.tourist.places.core.service.CommonService;
import at.backend.tourist.places.core.shared.Enum.Continent;

import java.util.List;

public interface CountryService extends CommonService<CountryDTO, CountryInsertDTO> {
    List<CountryDTO> getByContinent(Continent continent);
    CountryDTO getByName(String name);

    }