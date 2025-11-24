package o.e.entity;

import java.sql.Date;

public record User(
        Long id,
        String firstName,
        String lastName,
        String email,
        String password,
        Date createdAt,
        Roles role
) {


}
