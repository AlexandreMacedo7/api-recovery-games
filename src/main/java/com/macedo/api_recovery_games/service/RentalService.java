package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.RentalNotFoundException;
import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.Rental;
import com.macedo.api_recovery_games.models.User;
import com.macedo.api_recovery_games.models.dtos.rentaldto.RentalDTO;
import com.macedo.api_recovery_games.models.dtos.rentaldto.RentalPatchDTO;
import com.macedo.api_recovery_games.models.mapper.RentalMapper;
import com.macedo.api_recovery_games.repository.RentalRepository;
import com.macedo.api_recovery_games.util.RentalCalculator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {


    private final RentalRepository repository;
    private final MachineService machineService;
    private final UserService userService;
    private final RentalCalculator rentalCalculator;
    private final RentalMapper mapper;


    public RentalService(RentalRepository repository, MachineService machineService, UserService userService, RentalCalculator rentalCalculator, RentalMapper mapper) {
        this.repository = repository;
        this.machineService = machineService;
        this.userService = userService;
        this.rentalCalculator = rentalCalculator;
        this.mapper = mapper;
    }

//    @Transactional
//    public RentalDTO saveRental(RentalDTO rentalDTO) {
//        Machine machine = validateMachine(rentalDTO.machineId());
//        checkAvailabilityForRental(machine);
//
//        User user = validateUser(rentalDTO.userId());
//
//        Rental rental = mapper.toEntity(rentalDTO);
//
//        rentalCalculator.calculateRentalTime(rental, machine.getHourlyRate(), rentalDTO.totalCost());
//
//        repository.save(rental);
//
//        addRentalForMachine(rental, machine);
//        updateAvailableStatusMachine(machine);
//        addRentalForUser(rental, user);
//
//        return mapper.toDTO(rental);
//    }

    @Transactional
    public RentalDTO patchRental(Long id, RentalPatchDTO patchDTO) {
        Optional<Rental> rentalOptional = Optional.ofNullable(repository.findById(id).orElseThrow(() -> new RentalNotFoundException(id)));
        return mapper.toDTO(updateFields(patchDTO, rentalOptional));
    }

    public RentalDTO getRentalById(Long id) {
        Rental rental = repository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
        return mapper.toDTO(rental);
    }

    public List<RentalDTO> getAllRental() {
        return mapper.toDTOList(repository.findAll());
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RentalNotFoundException(id);
        }
    }

    private Rental updateFields(RentalPatchDTO patchDTO, Optional<Rental> rentalOptional) {
        Rental rental = rentalOptional.get();

        patchDTO.totalCost().ifPresent(rental::setTotalCost);

        repository.save(rental);
        return rental;
    }

    private Machine validateMachine(Long idMachine) {
        return machineService.validateById(idMachine);
    }

    private User validateUser(Long id) {
        return userService.validateById(id);
    }

    private void checkAvailabilityForRental(Machine machine) {
        machineService.checkAvailabilityForRental(machine);
    }

    private void updateAvailableStatusMachine(Machine machine) {
        machineService.markMachineAsUnavailable(machine);
    }

    private void addRentalForMachine(Rental rental, Machine machine) {
        machineService.addRental(rental, machine);
    }

    private void addRentalForUser(Rental rental, User user) {
        userService.addRental(rental, user);
    }
}
