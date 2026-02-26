package at.backend.tourist.places.modules.user.auto_mappers;

import at.backend.tourist.places.modules.auth.dto.SignupDTO;
import at.backend.tourist.places.modules.user.dtos.UserDTO;
import at.backend.tourist.places.modules.user.dtos.UserInsertDTO;
import at.backend.tourist.places.modules.user.model.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-26T12:37:48-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Homebrew)"
)
@Component
public class UserMappersImpl implements UserMappers {

    @Override
    public UserDTO entityToDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setRole( user.getRole() );

        return userDTO;
    }

    @Override
    public User DTOToEntity(SignupDTO signupDTO) {
        if ( signupDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( signupDTO.getEmail() );
        user.setName( signupDTO.getName() );
        user.setPassword( signupDTO.getPassword() );

        return user;
    }

    @Override
    public User DTOToEntity(UserInsertDTO insertDTO) {
        if ( insertDTO == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( insertDTO.getEmail() );
        user.setName( insertDTO.getName() );
        user.setPassword( insertDTO.getPassword() );
        user.setRole( insertDTO.getRole() );

        return user;
    }
}
