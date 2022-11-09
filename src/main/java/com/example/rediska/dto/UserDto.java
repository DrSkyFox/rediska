package com.example.rediska.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class UserDto {

    private String userId;

    private String userName;

    private String phoneNumber;
}
