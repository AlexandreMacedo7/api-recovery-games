package com.macedo.api_recovery_games.models.mapper;

import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.dtos.MachineDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MachineMapper {

    MachineDTO toDTO(Machine machine);

    Machine toEntity(MachineDTO machineDTO);

    List<MachineDTO> toDTOList(List<Machine> machineList);

    List<Machine> toEntityList(List<MachineDTO> machineDTOS);
}
