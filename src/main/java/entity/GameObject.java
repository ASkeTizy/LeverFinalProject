package entity;

import java.util.Date;

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
