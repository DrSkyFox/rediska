package com.example.rediska.repositories;

import com.example.rediska.entities.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.StringJoiner;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final ReactiveRedisOperations<String, UserInfo> redisOperationsUser;


    private final static String prefix = "user";

    public Mono<Boolean> save(UserInfo userInfo) {
        return redisOperationsUser.opsForValue().set(buildKey(userInfo), userInfo);
    }

    public Mono<UserInfo> get(String key) {
        return redisOperationsUser.opsForValue().get(buildKey(key));
    }

    private static String buildKey(UserInfo userInfo) {
        return buildKey(userInfo.getUserId());
    }

    private static String buildKey(String userId) {
        StringJoiner joiner= new StringJoiner(":");
        return joiner.add(prefix).add(userId).toString();
    }


}
