package o.e.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerificationService {

    private final StringRedisTemplate redisTemplate;

    public VerificationService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String generateCode(String email) {
        String code = String.format("%06d", new Random().nextInt(999999));
        redisTemplate.opsForValue().set("verify:" + email, code, 5, TimeUnit.MINUTES);
        return code;
    }

    public boolean verifyCode(String email, String code) {
        String stored = redisTemplate.opsForValue().get("verify:" + email);
        return stored != null && stored.equals(code);
    }
}
