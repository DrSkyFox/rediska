package com.example.rediska.config;

import com.example.rediska.entities.Transfer;
import com.example.rediska.entities.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.ReactiveRedisMessageListenerContainer;
import org.springframework.data.redis.serializer.*;

@Configuration
public class AppConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${spring.redis.chanelTopic}")
    private String chanelTopic;



    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration();
        redisStandaloneConfig = new RedisStandaloneConfiguration();
        redisStandaloneConfig.setHostName(redisHost);
        redisStandaloneConfig.setPort(redisPort);
        redisStandaloneConfig.setPassword(redisPassword);
        return new LettuceConnectionFactory(redisStandaloneConfig);
    }

    @Bean
    public ReactiveRedisOperations<String, UserInfo> redisOperationsUser(LettuceConnectionFactory connectionFactory) {
        RedisSerializationContext<String, UserInfo> serializationContext = RedisSerializationContext
                .<String, UserInfo>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new Jackson2JsonRedisSerializer<UserInfo>(UserInfo.class))
                .hashKey(new StringRedisSerializer())
                .hashValue(new Jackson2JsonRedisSerializer<UserInfo>(UserInfo.class))
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    public ReactiveRedisOperations<Transfer, String> redisOperationsTransfer(LettuceConnectionFactory connectionFactory) {
        RedisSerializationContext<Transfer, String> serializationContext = RedisSerializationContext
                .<Transfer, String>newSerializationContext(new StringRedisSerializer())
                .key(new Jackson2JsonRedisSerializer<Transfer>(objectMapper(),Transfer.class))
                .value(new StringRedisSerializer())
                .hashKey(new Jackson2JsonRedisSerializer<Transfer>(Transfer.class))
                .hashValue(new StringRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    @Bean
    @Primary
    public ReactiveRedisOperations<String, String> redisOperationsTop(LettuceConnectionFactory connectionFactory) {
        RedisSerializationContext<String, String> serializationContext = RedisSerializationContext
                .<String, String>newSerializationContext(new StringRedisSerializer())
                .key(new StringRedisSerializer())
                .value(new StringRedisSerializer())
                .hashKey(new StringRedisSerializer())
                .hashValue(new StringRedisSerializer())
                .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

    //------------------------ SERIALIZATION ------------------------
    @Bean
    public RedisSerializer<UserInfo> userInfoRedisSerializer() {
        return new Jackson2JsonRedisSerializer<UserInfo>(UserInfo.class);
    }
    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    public RedisSerializer<Transfer> transferRedisSerializer() {
        return new GenericToStringSerializer<>(Transfer.class);
    }
    //---------------- SUBSCRIBE ---------------
    @Bean
    public ReactiveRedisMessageListenerContainer container(LettuceConnectionFactory factory) {
        return new ReactiveRedisMessageListenerContainer(factory);
    }
    @Bean
    public ChannelTopic channelTopic() {
        return ChannelTopic.of(chanelTopic);
    }
}
