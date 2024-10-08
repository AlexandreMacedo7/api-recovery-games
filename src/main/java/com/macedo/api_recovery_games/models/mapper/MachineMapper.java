package com.macedo.api_recovery_games.models.mapper;

import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.dtos.machinedto.CreateMachineDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.MachineResponseDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.SimpleMachineResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MachineMapper {

    CreateMachineDTO toDTO(Machine machine);
    SimpleMachineResponseDTO toSimpleDTO(Machine machine);

    MachineResponseDTO toResponseDTO(Machine machine);

    Machine toEntity(CreateMachineDTO createMachineDTO);

    List<MachineResponseDTO> toDTOList(List<Machine> machineList);
    List<SimpleMachineResponseDTO> toDTOSimpleList(List<Machine> machineList);

    List<Machine> toEntityList(List<CreateMachineDTO> createMachineDTOS);
}
