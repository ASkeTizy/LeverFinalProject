package o.e.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {

    private final RedisTemplate<String, Object> redisTemplate;

    public VerificationService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        redisTemplate.opsForValue().set("verify:" + email, code, 5, TimeUnit.DAYS);
        return code;
    }

    public boolean verifyCode(String email, String code) {
        var stored = redisTemplate.opsForValue().get("verify:" + email);
        if(stored == null) {
            throw new NoSuchElementException("Redis error no such element");
        }
        return  stored.equals(code);
    }
}
