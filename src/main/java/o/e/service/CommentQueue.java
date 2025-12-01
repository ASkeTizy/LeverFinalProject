package o.e.service;

import o.e.dto.CommentDTO;
import o.e.entity.Comment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class CommentQueue {
    private final RedisTemplate<String, Object> redisTemplate;

    public CommentQueue(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addComment(Long userId, CommentDTO commentDTO) {
        Long id = redisTemplate.opsForValue().increment("comment:id:seq");
        redisTemplate.opsForValue().set("comment" + id, commentDTO);

    }

    public Comment removeComment(Integer commentId) {
        String key = "comment" + commentId;
        Comment comment = (Comment) redisTemplate.opsForValue().get(key);
        if (comment != null) {
            redisTemplate.delete(key);
            return comment;
        } else throw new NoSuchElementException("No element");
    }

}
