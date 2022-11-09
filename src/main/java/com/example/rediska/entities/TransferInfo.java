package com.example.rediska.entities;

import lombok.Data;

@Data
public class TransferInfo {

    private Transfer transfer;

    private UserInfo targetUser;

}
