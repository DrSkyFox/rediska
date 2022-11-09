package com.example.rediska;

import com.example.rediska.controller.TransferController;
import com.example.rediska.dto.TransferDto;
import com.example.rediska.dto.TransferInfoDto;
import com.example.rediska.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class RediskaApplicationTests {

    @Autowired
    private TransferController controller;
    @Test
    void contextLoads() {
        TransferInfoDto transferInfoDto = TransferInfoDto.builder()
                .transfer(TransferDto.builder()
                        .userId("111")
                        .targetUserId("222")
                        .build())
                .targetUser(UserDto.builder()
                        .phoneNumber("79996665544")
                        .userName("John Doe")
                        .userId("222")
                        .build())
                .build();
        Mono<Boolean> test = controller.saveMono(Mono.just(transferInfoDto));

        StepVerifier.create(test)
                .expectNext(Boolean.TRUE)
                .expectComplete()
                .verify();
    }

}
