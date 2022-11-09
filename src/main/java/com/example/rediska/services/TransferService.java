package com.example.rediska.services;

import com.example.rediska.dto.TransferInfoDto;
import com.example.rediska.entities.TransferInfo;
import com.example.rediska.mappers.TransferMapper;
import com.example.rediska.repositories.TransferRepository;
import com.example.rediska.repositories.TransferTopRepository;
import com.example.rediska.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final TransferTopRepository topRepository;
    private final TransferMapper mapper;

    public Mono<Boolean> save(TransferInfoDto dto) {
        return save(Mono.just(dto));
    }
    public Mono<Boolean> save(Mono<TransferInfoDto> dto) {
        return dto.map(mapper::fromTransferInfoDto).flatMap(transferInfo ->  userRepository.save(transferInfo.getTargetUser())
                .then(transferRepository.save(transferInfo.getTransfer()))
                .then(topRepository.save(transferInfo.getTransfer().getUserId(), transferInfo.getTransfer().getTargetUserId())));
    }

    public Mono<Boolean> saveAll(Flux<TransferInfoDto> dto) {
        return dto
                .flatMap(m -> Mono.just(mapper.fromTransferInfoDto(m)))
                .flatMap(transferInfo ->  userRepository.save(transferInfo.getTargetUser())
                .then(transferRepository.save(transferInfo.getTransfer()))
                .then(topRepository.save(transferInfo.getTransfer().getUserId(), transferInfo.getTransfer().getTargetUserId()))).then(Mono.just(Boolean.TRUE));
    }
}
