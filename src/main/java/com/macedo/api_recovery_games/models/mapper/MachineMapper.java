package com.macedo.api_recovery_games.models.mapper;

import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.dtos.CreateMachineDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MachineMapper {

    CreateMachineDTO toDTO(Machine machine);

    Machine toEntity(CreateMachineDTO createMachineDTO);

    List<CreateMachineDTO> toDTOList(List<Machine> machineList);

    List<Machine> toEntityList(List<CreateMachineDTO> createMachineDTOS);
}
