package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.MachineNotAvailableException;
import com.macedo.api_recovery_games.exception.MachineNotFoundException;
import com.macedo.api_recovery_games.models.Control;
import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.Rental;
import com.macedo.api_recovery_games.models.dtos.*;
import com.macedo.api_recovery_games.models.mapper.ControlMapper;
import com.macedo.api_recovery_games.models.mapper.MachineMapper;
import com.macedo.api_recovery_games.models.mapper.RentalMapper;
import com.macedo.api_recovery_games.repository.MachineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper mapper;
    private final RentalMapper rentalMapper;
    private final ControlMapper controlMapper;

    @Autowired
    public MachineService(MachineRepository machineRepository, MachineMapper mapper, RentalMapper rentalMapper, ControlMapper controlMapper) {
        this.machineRepository = machineRepository;
        this.mapper = mapper;
        this.rentalMapper = rentalMapper;
        this.controlMapper = controlMapper;
    }
    // TODO: Melhorar responsabilidade do método
    // TODO: Realizar validação de entrada
    @Transactional
    public MachineResponseDTO saveMachine(CreateMachineDTO createMachineDTO) {
        Machine machine = mapper.toEntity(createMachineDTO);
        List<Control> controls = controlMapper.toEntityList(createMachineDTO.createControlDTOList());
        for (Control control : controls) {
            machine.addControl(control);
        }
        machineRepository.save(machine);
        List<ControlResponseDTO> controlResponseDTOSDTOList = controlMapper.toDTOList(machine.getControls());
        return new MachineResponseDTO(machine.getId(),machine.getType(), controlResponseDTOSDTOList);
    }

    @Transactional
    public CreateMachineDTO patchMachine(Long id, MachinePatchDTO dto) {
        Optional<Machine> machineOptional = Optional.ofNullable(machineRepository.findById(id)
                .orElseThrow(() -> new MachineNotFoundException(id)));

        return mapper.toDTO(fieldUpdate(dto, machineOptional));
    }

    public CreateMachineDTO getMachineById(Long id) {
        Machine machine = machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException(id));
        return mapper.toDTO(machine);
    }

    public List<RentalDTO> getRentalsByMachineId(Long id) {
        Machine machine = validateById(id);
        return rentalMapper.toDTOList(machine.getRentals());
    }

    public List<CreateMachineDTO> getAllMachines() {
        List<Machine> machineList = machineRepository.findAll();
        return mapper.toDTOList(machineList);
    }

    public void deleteMachineById(Long id) throws MachineNotFoundException {
        if (machineRepository.existsById(id)) {
            machineRepository.deleteById(id);
        } else {
            throw new MachineNotFoundException(id);
        }
    }

    private Machine fieldUpdate(MachinePatchDTO machinePatchDTO, Optional<Machine> machineOptional) {

        Machine machine = machineOptional.get();

        machinePatchDTO.type().ifPresent(machine::setType);

        machineRepository.save(machine);
        return machine;
    }

    public Machine validateById(Long id) {
        return machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException(id));
    }

    public void checkAvailabilityForRental(Machine machine) {
        if (!machine.isAvailable()) throw new MachineNotAvailableException(machine.getId());
    }

    public void markMachineAsUnavailable(Machine machine) {
        machine.setAvailable(false);
        machineRepository.save(machine);
    }

    public void addRental(Rental rental, Machine machine) {
        machine.addRental(rental);
        machineRepository.save(machine);
    }
}
