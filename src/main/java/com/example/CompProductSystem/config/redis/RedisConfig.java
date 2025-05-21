package com.example.CompProductSystem.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    // Redis 관련 설정을 추가할 수 있는 클래스입니다.
    // 현재는 기본 설정만 사용하고 있습니다.
    // 필요에 따라 RedisTemplate, RedisConnectionFactory 등을 설정할 수 있습니다.
    // 예를 들어, RedisTemplate을 설정하려면 다음과 같이 작성할 수 있습니다.

    // RedisConnectionFactory 생성
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 서버의 호스트와 포트 설정
        // application.properties에서 설정간 값은 @Value 어노테이션을 통해 주입받을 수도 있습니다.
        RedisStandaloneConfiguration redisStandaloneConfiguration =
                new RedisStandaloneConfiguration("localhost", 6379);
        // 연결 팩토리를 lettuce로 설정합니다.
        return new LettuceConnectionFactory(redisStandaloneConfiguration); // LettuceConnectionFactory는 비동기 및 반응형 Redis 클라이언트입니다.
    }

    // RedisConnectionFactory를 주입받아 RedisTemplate을 설정합니다.
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // 키와 값의 직렬화 방식을 설정합니다.
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        // 또 다른 유용한 설정

        return template;
    }
}
