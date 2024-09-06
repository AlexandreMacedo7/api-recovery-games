package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.ControlNotFoundException;
import com.macedo.api_recovery_games.exception.MachineNotAvailableException;
import com.macedo.api_recovery_games.exception.MachineNotFoundException;
import com.macedo.api_recovery_games.models.Control;
import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.Rental;
import com.macedo.api_recovery_games.models.dtos.controldto.ControlResponseDTO;
import com.macedo.api_recovery_games.models.dtos.controldto.CreateControlDTO;
import com.macedo.api_recovery_games.models.dtos.controldto.UpdateControlDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.CreateMachineDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.MachinePatchDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.MachineResponseDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.SimpleMachineResponseDTO;
import com.macedo.api_recovery_games.models.dtos.rentaldto.RentalDTO;
import com.macedo.api_recovery_games.models.mapper.ControlMapper;
import com.macedo.api_recovery_games.models.mapper.MachineMapper;
import com.macedo.api_recovery_games.models.mapper.RentalMapper;
import com.macedo.api_recovery_games.repository.MachineRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;
    private final RentalMapper rentalMapper;
    private final ControlMapper controlMapper;

    @Autowired
    public MachineService(MachineRepository machineRepository, MachineMapper machineMapper, RentalMapper rentalMapper, ControlMapper controlMapper) {
        this.machineRepository = machineRepository;
        this.machineMapper = machineMapper;
        this.rentalMapper = rentalMapper;
        this.controlMapper = controlMapper;
    }

    // TODO: Melhorar responsabilidade do método
    // TODO: Realizar validação de entrada
    @Transactional
    public MachineResponseDTO saveMachine(CreateMachineDTO createMachineDTO) {
        Machine machine = machineMapper.toEntity(createMachineDTO);
        List<Control> controls = controlMapper.toEntityList(createMachineDTO.createControlDTOList());
        for (Control control : controls) {
            machine.addControl(control);
        }
        machineRepository.save(machine);
        List<ControlResponseDTO> controlResponseDTOSDTOList = controlMapper.toDTOList(machine.getControls());
        return new MachineResponseDTO(machine.getId(), machine.getType(), controlResponseDTOSDTOList);
    }

    @Transactional
    public SimpleMachineResponseDTO updateTypeMachine(Long id, MachinePatchDTO dto) {
        var machine = machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException(id));
        return machineMapper.toSimpleDTO(fieldUpdate(dto, machine));
    }

    @Transactional

    public MachineResponseDTO createControl(Long id, CreateControlDTO dto) {
        Machine machine = validateById(id);
        validateValuesControl(dto, machine);
        Control control = controlMapper.toEntity(dto);
        machine.addControl(control);
        Machine machineSave = machineRepository.save(machine);
        List<ControlResponseDTO> controlResponseDTOList = controlMapper.toDTOList(machine.getControls());
        return new MachineResponseDTO(machineSave.getId(), machineSave.getType(), controlResponseDTOList);
    }

    //TODO Refatorar método para melhorar suas responsabilidades e legibilidade
    @Transactional
    public MachineResponseDTO updateValueControl(Long idMachine, UpdateControlDTO controlDTO) {
        Machine machine = validateById(idMachine);

        Control control = machine.getControls().stream()
                .filter(c -> c.getId().equals(controlDTO.id()))
                .findFirst()
                .orElseThrow(() -> new ControlNotFoundException(controlDTO.id()));

        control.setValueControl(controlDTO.valueControl());
        machineRepository.save(machine);
        List<ControlResponseDTO> detailsControlDTOList = controlMapper.toDTOList(machine.getControls());
        return new MachineResponseDTO(machine.getId(), machine.getType(), detailsControlDTOList);
    }

    public MachineResponseDTO getMachineById(Long id) {
        Machine machine = machineRepository.findById(id).orElseThrow(() -> new MachineNotFoundException(id));
        List<ControlResponseDTO> controlResponseDTOS = controlMapper.toDTOList(machine.getControls());
        return new MachineResponseDTO(machine.getId(), machine.getType(), controlResponseDTOS);
    }

    public List<RentalDTO> getRentalsByMachineId(Long id) {
        Machine machine = validateById(id);
        return rentalMapper.toDTOList(machine.getRentals());
    }

    public List<SimpleMachineResponseDTO> getAllMachines() {
        List<Machine> machineList = machineRepository.findAll();
        return machineMapper.toDTOSimpleList(machineList);
    }

    public void deleteMachineById(Long id) throws MachineNotFoundException {
        if (machineRepository.existsById(id)) {
            machineRepository.deleteById(id);
        } else {
            throw new MachineNotFoundException(id);
        }
    }

    private Machine fieldUpdate(MachinePatchDTO machinePatchDTO, Machine machine) {
        machine.setType(machinePatchDTO.type());
        machineRepository.save(machine);
        return machine;
    }
    /*TODO: Validar valores de controles - onde controle 1 precisa ser menor que 2, e 2 não pode ser maior que 3
     1 = 5 , 2 = 10, 3 =15
    */
    private void validateValuesControl(CreateControlDTO dto, Machine machine){
        boolean isPresent = machine.getControls()
                .stream().anyMatch(control -> control.getNumber().equals(dto.number()));
        if (isPresent){
            throw new IllegalArgumentException("Já existe um controle com número: " + dto.number());
        }
        boolean isSmaller = machine.getControls()
                .stream().anyMatch(control -> control.getValueControl() > dto.valueControl());
        if (isSmaller){
            throw new IllegalArgumentException("O valor do controle não pode ser menor que os controles anteriores: " + dto.valueControl());
        }
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
