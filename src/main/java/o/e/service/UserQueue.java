package o.e.service;

import o.e.dto.CommentDTO;
import o.e.dto.UserDTO;
import o.e.entity.Comment;
import o.e.entity.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Component
public class UserQueue {
    private final RedisTemplate<String, Object> redisTemplate;

    public UserQueue(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addUser(User user) {
        Long id = redisTemplate.opsForValue().increment("user:id:seq");
        redisTemplate.opsForValue().set("user" + id, user);

    }

    public User getUser(Long userId) {
        String key = "user" + userId;
        User user = (User) redisTemplate.opsForValue().get(key);
        if (user != null) {
            redisTemplate.delete(key);
            return user;
        } else throw new NoSuchElementException("No element");

    }
    public List<User> getAllUsers() {
        Set<String> keys = redisTemplate.keys("user:*");
        return redisTemplate.opsForValue().multiGet(keys).stream()
                .map(json -> objectMapper.readValue(json, Comment.class))
                .toList();;
    }
}
