package at.backend.tourist.places.modules.auth.jwt.Token;

public interface TokenGenerator {
    String generateToken(String email, Long userId, String role);
}
