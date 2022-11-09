package com.example.rediska.entities;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Transfer {

    private String userId;
    private String targetUserId;
    private LocalDateTime dateTime;

}
