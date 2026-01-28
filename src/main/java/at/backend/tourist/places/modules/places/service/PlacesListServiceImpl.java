package at.backend.tourist.places.modules.places.service;

import at.backend.tourist.places.core.exceptions.BusinessLogicException;
import at.backend.tourist.places.core.exceptions.ResourceNotFoundException;
import at.backend.tourist.places.modules.places.auto_mappers.PlaceListMapper;
import at.backend.tourist.places.modules.places.dto.PlaceListDTO;
import at.backend.tourist.places.modules.places.dto.PlaceListInsertDTO;
import at.backend.tourist.places.modules.places.model.PlaceList;
import at.backend.tourist.places.modules.places.model.TouristPlace;
import at.backend.tourist.places.modules.places.repository.PlaceListRepository;
import at.backend.tourist.places.modules.places.repository.TouristPlaceRepository;
import at.backend.tourist.places.modules.user.model.User;
import at.backend.tourist.places.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlacesListServiceImpl implements PlaceListService {
    
    private final PlaceListRepository placeListRepository;
    private final TouristPlaceRepository placeRepository;
    private final PlaceListMapper listMapper;
    private final UserRepository userRepository;

    @Override
    public PlaceListDTO getById(Long id) {
        Optional<PlaceList> optionalPlaceList = placeListRepository.findById(id);
        return optionalPlaceList
                .map(listMapper::entityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Place List", "id", id));
    }

    @Override
    public List<PlaceListDTO> getAll() {
        List<PlaceList> placeList =  placeListRepository.findAll();

        return placeList.stream()
                .map(listMapper::entityToDTO)
                .toList();
    }

    @Override
    public List<PlaceListDTO> getByUserId(Long userId) {
        List<PlaceList> placeLists = placeListRepository.findByUserId(userId);

        return placeLists.stream()
                .map(listMapper::entityToDTO)
                .toList();
    }


    @Transactional
    @Override
    public PlaceListDTO create(PlaceListInsertDTO insertDTO) {
        PlaceList placeList = listMapper.DTOToEntity(insertDTO);

        validate(insertDTO);

        placeList.setPlaces(getPlaces(insertDTO.getPlacesIds()));
        placeList.setUser(getUser(insertDTO.getUserId()));

        placeListRepository.saveAndFlush(placeList);

        return listMapper.entityToDTO(placeList);
    }

    @Override
    @Transactional
    public PlaceListDTO addPlaces(Long placeListId, Set<Long> placeIds) {
        PlaceList placeList = placeListRepository.findById(placeListId)
                .orElseThrow(() -> new ResourceNotFoundException("Place Category", "id", placeListId));

        Set<TouristPlace> placesToAdd = getPlaces(placeIds);

        placeList.getPlaces().addAll(placesToAdd);

        placeListRepository.saveAndFlush(placeList);

        return listMapper.entityToDTO(placeList);
    }

    @Override
    public void delete(Long id, Long userId) {
        Optional<PlaceList> placeList = placeListRepository.findByIdAndUserId(id, userId);
        if (placeList.isEmpty()) {
            throw new ResourceNotFoundException("Place Category", "id", id);
        }

        placeListRepository.deleteById(id);
    }

    @Override
    @Transactional
    public PlaceListDTO removePlaces(Long placeListId, Set<Long> placeIds) {
        PlaceList placeList = placeListRepository.findById(placeListId)
                .orElseThrow(() -> new ResourceNotFoundException("Place List", "id", placeListId));

        List<TouristPlace> placesToRemove = placeList.getPlaces().stream()
                .filter(place -> placeIds.contains(place.getId()))
                .toList();

        placesToRemove.forEach(placeList.getPlaces()::remove);

        placeListRepository.saveAndFlush(placeList);

        return listMapper.entityToDTO(placeList);
    }


    @Override
    public void delete(Long id) {
        boolean exists = placeListRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Place List", "id", id);
        }

        placeListRepository.deleteById(id);
    }


    public void validate(PlaceListInsertDTO insertDTO) {
        int incomingIds = insertDTO.getPlacesIds().size();
        if (incomingIds >= 100) {
            throw new BusinessLogicException("Max limit of places reached. Limit = 100");
        }

        User user = userRepository.findById(insertDTO.getUserId()).orElseThrow( () -> new ResourceNotFoundException("user", "id", insertDTO.getUserId()));
        int listCount = placeListRepository.findByUserId(user.getId()).size();
        if (listCount > 10) {
            throw new BusinessLogicException("Max number of lists reached. Limit = 10");
        }

    }

    private Set<TouristPlace> getPlaces(Set<Long> idsList) {
        Set<TouristPlace> touristPlaces = placeRepository.findByIdIn(idsList);

        Set<Long> foundIds = touristPlaces.stream()
                .map(TouristPlace::getId)
                .collect(Collectors.toSet());

        List<Long> missingIds = idsList.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            throw new ResourceNotFoundException("Not found IDs for places: " + missingIds);
        }

        return touristPlaces;
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
    }
}
