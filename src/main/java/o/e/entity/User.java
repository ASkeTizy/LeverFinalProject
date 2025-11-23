package o.e.entity;

import java.sql.Date;

public record User(
        Long id,
        String firstName,
        String lastName,
        String password,
        String email,
        Date createdAt,
        Roles role
) {


}
