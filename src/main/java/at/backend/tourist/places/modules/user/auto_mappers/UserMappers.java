package at.backend.tourist.places.modules.user.auto_mappers;

import at.backend.tourist.places.modules.auth.dto.SignupDTO;
import at.backend.tourist.places.modules.user.dtos.UserDTO;
import at.backend.tourist.places.modules.user.dtos.UserInsertDTO;
import at.backend.tourist.places.modules.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMappers {

    UserDTO entityToDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "joinedAt", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    User DTOToEntity(SignupDTO signupDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "provider", ignore = true)
    @Mapping(target = "joinedAt", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    User DTOToEntity(UserInsertDTO insertDTO);


}