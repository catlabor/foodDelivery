package com.food.order.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 设置Key的序列化方式为StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 设置Value的序列化方式为Jackson2JsonRedisSerializer
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        // 设置Hash Key的序列化方式为StringRedisSerializer
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        // 设置Hash Value的序列化方式为Jackson2JsonRedisSerializer
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        ObjectMapper om=new ObjectMapper();
        om.registerModule(new JavaTimeModule());
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        om.setTimeZone(TimeZone.getDefault());
//        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
//        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
