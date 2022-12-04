package redis;

import com.alibaba.fastjson.JSON;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisCache {
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean setInv(String fpqqlsh , Object v){
        return set(InvKeyGenerator.genKey(fpqqlsh), JSON.toJSONString(v), 10L,TimeUnit.MINUTES);
    }

    public boolean set(final String key, Object value, Long expireTime , TimeUnit timeUnit) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Object getInv(final String key) {
        return get(InvKeyGenerator.genKey(key));
    }

    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

}
