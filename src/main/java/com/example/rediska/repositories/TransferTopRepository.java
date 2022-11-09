package com.example.rediska.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.StringJoiner;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TransferTopRepository {

    private final ReactiveRedisOperations<String, String> redisOperations;
    private static final String prefix = "top";

    public Mono<Boolean> save(String key, String value) {
        return redisOperations.opsForZSet().incrementScore(key, value, 1.0).then(Mono.just(Boolean.TRUE));
    }

    public Mono<Boolean> decreaseScore(String key, String value, Integer delta) {
        return redisOperations.opsForZSet().incrementScore(key, value, delta)
                .filter(aDouble -> aDouble <=0)
                .then(redisOperations.opsForZSet().remove(key, value))
                .then(Mono.just(Boolean.TRUE));
    }

    private static String buildKey(String key) {
        StringJoiner joiner= new StringJoiner(":");
        return joiner.add(prefix).add(key).toString();
    }
}
