package o.e.dto;

public record VerifiedUserDTO(
        String code,
        String email,
        String password
) {
}
