package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.RentalNotFoundException;
import com.macedo.api_recovery_games.models.Rental;
import com.macedo.api_recovery_games.models.dtos.RentalDTO;
import com.macedo.api_recovery_games.models.dtos.RentalPatchDTO;
import com.macedo.api_recovery_games.models.mapper.RentalMapper;
import com.macedo.api_recovery_games.repository.RentalRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository repository;
    private final MachineService machineService;
    private final UserService userService;
    private final RentalMapper mapper;


    public RentalService(RentalRepository repository, MachineService machineService, UserService userService, RentalMapper mapper) {
        this.repository = repository;
        this.machineService = machineService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Transactional
    public RentalDTO saveRental(RentalDTO rentalDTO) {

        validateMachineAndUser(rentalDTO.machineId(), rentalDTO.userId());

        Rental rental = mapper.toEntity(rentalDTO);
        repository.save(rental);

        return mapper.toDTO(rental);
    }

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

        patchDTO.startTime().ifPresent(rental::setStartTime);
        patchDTO.endTime().ifPresent(rental::setEndTime);
        patchDTO.totalCost().ifPresent(rental::setTotalCost);

        repository.save(rental);
        return rental;
    }

    private void validateMachineAndUser(Long idMachine, Long idUser) {
        userService.validateById(idUser);
        machineService.validateById(idMachine);
    }
}
