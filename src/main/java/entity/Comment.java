package entity;

import java.sql.Date;

public record Comment (
    Long id,
    String message,
    Long authorId,
    Date created_at,
    Integer rate
){
}
