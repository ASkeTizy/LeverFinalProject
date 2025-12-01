package o.e.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        user.setId(id);

        redisTemplate.opsForValue().set("user:entity" + id, user);
    }

    public User getUserByEmail(String email) {
        User user = (User) redisTemplate.opsForValue().get("user:email" + email);
        return user;
    }

    public User getUser(Long userId) {
        String key = "user:entity" + userId;
        User user = (User) redisTemplate.opsForValue().get(key);
        if (user != null) {
            redisTemplate.opsForValue().set("user:email" + user.getEmail(), user);
            redisTemplate.delete(key);
            return user;
        } else throw new NoSuchElementException("No element");

    }

    public List<User> getAllUsers() {
        var mapper = new ObjectMapper();
        Set<String> keys = redisTemplate.keys("user:entity*");
        return keys.stream()
                .map(key -> (User) redisTemplate.opsForValue().get(key))
                .toList();
    }
}
