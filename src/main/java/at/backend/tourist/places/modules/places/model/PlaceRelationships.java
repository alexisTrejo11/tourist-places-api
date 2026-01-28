package at.backend.tourist.places.modules.places.model;

import at.backend.tourist.places.modules.country.model.Country;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRelationships {
    public Country country;
    public PlaceCategory placeCategory;

}
