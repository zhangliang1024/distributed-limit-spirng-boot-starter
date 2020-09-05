package com.zhliang.pzy.distributed.limit.config;

import com.zhliang.pzy.distributed.limit.generator.DefaultRedisKeyGenerator;
import com.zhliang.pzy.distributed.limit.generator.RedisKeyGenerator;
import com.zhliang.pzy.distributed.limit.aspect.RedisLimitAspect;
import com.zhliang.pzy.distributed.limit.handler.LimitHandler;
import com.zhliang.pzy.distributed.limit.handler.LocalLimitHandler;
import com.zhliang.pzy.distributed.limit.handler.RedisLimitHandler;
import com.zhliang.pzy.distributed.limit.type.LimitType;
import com.zhliang.pzy.distributed.limit.type.LimitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@ConditionalOnClass(RedisAutoConfiguration.class)
@EnableConfigurationProperties(EnableLiimitProperties.class)
@Import({RedisLimitAspect.class})
@ConditionalOnProperty(prefix = "pzy.redis.limit", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RedisLimitAutoConfiguration {

    @Autowired
    private EnableLiimitProperties prop;

    @Bean
    @ConditionalOnMissingBean
    public RedisKeyGenerator redisKeyGenerator() {
        return new DefaultRedisKeyGenerator();
    }

    @Bean("redisLimitTemplate")
    @ConditionalOnProperty(prefix = "pzy.redis.limit.type", name = "redis", havingValue = "true")
    public RedisTemplate<String, Serializable> redisLimitTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> redisLimitTemplate = new RedisTemplate<>();
        redisLimitTemplate.setKeySerializer(new StringRedisSerializer());
        redisLimitTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisLimitTemplate.setConnectionFactory(redisConnectionFactory);
        return redisLimitTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public LimitHandler limitHandler(){
        if(LimitType.REDIS.getName().equals(prop.getType())){
            return  new RedisLimitHandler();
        }else if(LimitType.LOCAL.getName().equals(prop.getType())){
            return  new LocalLimitHandler();
        }
        return  new LocalLimitHandler();
    }
}