package com.macedo.api_recovery_games.models.mapper;

import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.User;
import com.macedo.api_recovery_games.models.dtos.MachineDTO;
import com.macedo.api_recovery_games.models.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    MachineDTO toDTO(User user);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "phone", target = "phone")
    Machine toEntity(MachineDTO machineDTO);

    List<UserDTO> toDTOList(List<User> users);

    List<User> toEntity(List<UserDTO> userDTOS);
}
