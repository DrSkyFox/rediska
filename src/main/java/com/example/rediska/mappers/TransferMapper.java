package com.example.rediska.mappers;

import com.example.rediska.dto.TransferDto;
import com.example.rediska.dto.TransferInfoDto;
import com.example.rediska.dto.UserDto;
import com.example.rediska.entities.Transfer;
import com.example.rediska.entities.TransferInfo;
import com.example.rediska.entities.UserInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    TransferInfo fromTransferInfoDto(TransferInfoDto dto);
    Transfer fromTransfer(TransferDto dto);
    UserInfo fromUserDto(UserDto dto);

}
