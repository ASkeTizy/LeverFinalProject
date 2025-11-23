package o.e.entity;

import java.sql.Date;

public record GameObject(
        Long id,
        String title,
        String text,
        Integer gameId,
        Long userId,
        Date createdAt,
        Date updatedAt
) {

}
