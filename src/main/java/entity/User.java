package entity;

import java.util.Date;

public record User(
        Long id,
        String firstName,
        String LastName,
        String password,
        String email,
        Date createdAt,
        Roles role
) {


}
