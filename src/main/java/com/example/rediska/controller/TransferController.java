package com.example.rediska.controller;

import com.example.rediska.dto.TransferInfoDto;
import com.example.rediska.services.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping(value = "/addMono", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> saveMono(@RequestBody Mono<TransferInfoDto> dto) {
        log.info("TransferDto: {}", dto);
        return transferService.save(dto);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> save(@RequestBody TransferInfoDto dto) {
        log.info("TransferDto: {}", dto);
        return transferService.save(dto);
    }

    @PostMapping(value = "/addAll", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> saveAll(@RequestBody Flux<TransferInfoDto> dto) {
        return transferService.saveAll(dto);
    }

}
