package com.example.rediska.repositories;

import com.example.rediska.entities.Transfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Repository
public class TransferRepository {

    private final ReactiveRedisOperations<Transfer, String> redisOperationsTransfer;

    private final static String prefix = "transfer";

    public Mono<Boolean> save(Transfer transfer) {
        transfer.setDateTime(LocalDateTime.now());
        return redisOperationsTransfer.opsForSet().add(transfer, "1")
                .then(redisOperationsTransfer.expire(transfer, Duration.ofDays(90)))
                .then(Mono.just(Boolean.TRUE));
    }

}
