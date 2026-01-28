package at.backend.tourist.places.modules.user.service;

import at.backend.tourist.places.core.exceptions.ResourceNotFoundException;
import at.backend.tourist.places.modules.user.auto_mappers.UserMappers;
import at.backend.tourist.places.modules.auth.dto.SignupDTO;
import at.backend.tourist.places.modules.user.dtos.UserDTO;
import at.backend.tourist.places.modules.user.dtos.UserInsertDTO;
import at.backend.tourist.places.modules.user.model.User;
import at.backend.tourist.places.modules.user.repository.UserRepository;
import at.backend.tourist.places.core.shared.Enum.Role;
import at.backend.tourist.places.core.shared.User.CustomOAuth2User;
import at.backend.tourist.places.core.shared.User.PasswordHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMappers userMappers;

    @Override
    public UserDTO getById(Long id) {
        Optional<User> user = userRepository.findById(id);

        return user.map(userMappers::entityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id));
    }

    @Override
    public Page<UserDTO> getByRole(Role role, Pageable pageable) {
        Page<User> userPage =  userRepository.findByRole(role, pageable);
        return userPage.map(userMappers::entityToDTO);
    }

    @Override
    public UserDTO getByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMappers::entityToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMappers::entityToDTO)
                .toList();
    }

    @Override
    public UserDTO create(SignupDTO signupDTO) {
       User user = userMappers.DTOToEntity(signupDTO);

       String hashedPassword = PasswordHandler.hashPassword(user.getPassword());
       user.setPassword(hashedPassword);
       user.setActivated(false);

       userRepository.saveAndFlush(user);

       return userMappers.entityToDTO(user);
    }



    @Override
    public UserDTO create(UserInsertDTO insertDTO) {
        User user = userMappers.DTOToEntity(insertDTO);

        String hashedPassword = PasswordHandler.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        user.setActivated(true);

        userRepository.saveAndFlush(user);

        return userMappers.entityToDTO(user);
    }

    @Override
    public void delete(Long id) {
        boolean isUserExisting = userRepository.existsById(id);
        if (!isUserExisting) {
            throw new EntityNotFoundException("user not found");
        }

        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found: " + email));

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        if (user.getPassword() == null) {
            return new CustomOAuth2User(user.getEmail(), user.getRole());
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }


    @Override
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user", "email", email));

        String hashedPassword = PasswordHandler.hashPassword(newPassword);
        user.setPassword(hashedPassword);

        userRepository.save(user);
    }

    @Override
    public void updateRole(Long id, Role role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", id));

        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public void activateUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user", "email", email));

        user.setActivated(true);

        userRepository.save(user);
    }
}
