package at.backend.tourist.places.modules.auth.service;

import at.backend.tourist.places.modules.auth.dto.LoginDTO;
import at.backend.tourist.places.modules.auth.dto.LoginResponseDTO;
import at.backend.tourist.places.modules.auth.dto.SignupDTO;
import at.backend.tourist.places.modules.user.dtos.UserDTO;

public interface AuthService {
    void validateSignup(SignupDTO signupDTO);
   UserDTO validateLogin(LoginDTO signupDTO);

    void processSignup(UserDTO signupDTO);
    LoginResponseDTO processLogin(UserDTO loginDTO);

    void invalidToken(String token);
    String processResetPassword(String email);
    String getEmailFromToken(String token);
    boolean isTokenValid(String token);
}
