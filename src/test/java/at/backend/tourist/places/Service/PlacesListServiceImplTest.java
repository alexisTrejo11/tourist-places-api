package at.backend.tourist.places.Service;

import at.backend.tourist.places.core.exceptions.ResourceNotFoundException;
import at.backend.tourist.places.modules.places.auto_mappers.PlaceListMapper;
import at.backend.tourist.places.modules.places.dto.PlaceListDTO;
import at.backend.tourist.places.modules.places.dto.PlaceListInsertDTO;
import at.backend.tourist.places.modules.places.model.PlaceList;
import at.backend.tourist.places.modules.places.repository.PlaceListRepository;
import at.backend.tourist.places.modules.places.repository.TouristPlaceRepository;
import at.backend.tourist.places.modules.places.service.PlacesListServiceImpl;
import at.backend.tourist.places.modules.user.model.User;
import at.backend.tourist.places.modules.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlacesListServiceImplTest {

    @Mock
    private PlaceListRepository placeListRepository;

    @Mock
    private TouristPlaceRepository placeRepository;

    @Mock
    private PlaceListMapper listMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PlacesListServiceImpl placeListService;

    private PlaceList placeList;
    private PlaceListDTO placeListDTO;
    private PlaceListInsertDTO placeListInsertDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        placeList = new PlaceList();
        placeList.setId(1L);
        placeList.setName("Favorite places");
        placeList.setUser(user);
        placeList.setPlaces(new HashSet<>());

        placeListDTO = new PlaceListDTO();
        placeListDTO.setId(1L);
        placeListDTO.setName("Favorite places");
        placeListDTO.setUserId(1L);

        placeListInsertDTO = new PlaceListInsertDTO();
        placeListInsertDTO.setName("Favorite places");
        placeListInsertDTO.setUserId(1L);
    }

    @Test
    void testGetById_Success() {
        when(placeListRepository.findById(1L)).thenReturn(Optional.of(placeList));
        when(listMapper.entityToDTO(placeList)).thenReturn(placeListDTO);

        PlaceListDTO result = placeListService.getById(1L);

        assertNotNull(result);
        assertEquals("Favorite places", result.getName());
    }

    @Test
    void testGetById_NotFound() {
        when(placeListRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> placeListService.getById(1L));
    }

    @Test
    void testCreate_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(listMapper.DTOToEntity(any(PlaceListInsertDTO.class))).thenReturn(placeList);
        when(placeListRepository.saveAndFlush(any(PlaceList.class))).thenReturn(placeList);
        when(listMapper.entityToDTO(any(PlaceList.class))).thenReturn(placeListDTO);

        PlaceListDTO result = placeListService.create(placeListInsertDTO);

        assertNotNull(result);
        assertEquals("Favorite places", result.getName());
        verify(placeListRepository, times(1)).saveAndFlush(any(PlaceList.class));
    }

    @Test
    void testCreate_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> placeListService.create(placeListInsertDTO));
    }

    @Test
    void testDelete_Success() {
        when(placeListRepository.existsById(1L)).thenReturn(true);

        placeListService.delete(1L);

        verify(placeListRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(placeListRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> placeListService.delete(1L));
    }
}
