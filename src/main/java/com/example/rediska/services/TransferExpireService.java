package com.example.rediska.services;

import com.example.rediska.entities.NotificationTransfer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferExpireService {

    private final ReactiveRedisMessageListenerContainer container;
    private final ChannelTopic channelTopic;

    @PostMapping
    private void init() {
        container.receive(channelTopic).subscribe();
    }

}
