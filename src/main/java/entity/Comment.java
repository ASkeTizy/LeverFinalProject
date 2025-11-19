package entity;

import java.util.Date;

public record Comment (
    Long id,
    String message,
    Long authorId,
    Date created_at
){
}
