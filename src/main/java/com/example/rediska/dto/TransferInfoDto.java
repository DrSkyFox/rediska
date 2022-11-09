package com.example.rediska.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class TransferInfoDto {

    private TransferDto transfer;

    private UserDto targetUser;
}
