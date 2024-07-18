package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.MachineNotFoundException;
import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.dtos.MachineDTO;
import com.macedo.api_recovery_games.models.dtos.MachinePatchDTO;
import com.macedo.api_recovery_games.models.mapper.MachineMapper;
import com.macedo.api_recovery_games.repository.MachineRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper mapper;

    @Autowired
    public MachineService(MachineRepository machineRepository, MachineMapper mapper) {
        this.machineRepository = machineRepository;
        this.mapper = mapper;
    }

    @Transactional
    public MachineDTO saveMachine(MachineDTO machineDTO) {
        Machine machine = mapper.toEntity(machineDTO);
        machineRepository.save(machine);
        return mapper.toDTO(machine);
    }

    @Transactional
    public MachineDTO patchMachine(Long id, MachinePatchDTO dto) {
        Optional<Machine> machineOptional = machineRepository.findById(id);

        return machineOptional.map(machine -> {
            Machine updatedMachine = fieldUpdate(dto, Optional.of(machine));
            return mapper.toDTO(updatedMachine);
        }).orElseThrow(() -> new MachineNotFoundException(id));
    }

    public MachineDTO getMachineById(Long id) {
        Machine machine = machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException(id));
        return mapper.toDTO(machine);
    }

    public List<MachineDTO> getAllMachines() {
        List<Machine> machineList = machineRepository.findAll();
        return mapper.toDTOList(machineList);
    }

    public void deleteMachineById(Long id) throws MachineNotFoundException {
        try {
            machineRepository.deleteById(id);
        } catch (EntityNotFoundException ex) {
            throw new MachineNotFoundException(id);
        }
    }

    private Machine fieldUpdate(MachinePatchDTO machinePatchDTO, Optional<Machine> machineOptional) {

        Machine machine = machineOptional.get();

        machinePatchDTO.type().ifPresent(machine::setType);
        machinePatchDTO.hourlyRate().ifPresent(machine::setHourlyRate);

        machineRepository.save(machine);
        return machine;
    }
}
