package com.macedo.api_recovery_games.service;

import com.macedo.api_recovery_games.exception.RentalNotFoundException;
import com.macedo.api_recovery_games.models.Machine;
import com.macedo.api_recovery_games.models.Rental;
import com.macedo.api_recovery_games.models.User;
import com.macedo.api_recovery_games.models.dtos.rentaldto.CreateRentalDTO;
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

    //RECEBE DADOS: ID MACHINE - NUMBER CONTROL PARA CALCULO DE VALOR, ID USUARIO, E TOTALCOST - VALOR PAGO PELO CLIENTE
    //CALCULAR QUANTAS HORAS SE PODE JOGAR COM UM CONTROLE COM VALOR X

    @Transactional
    public CreateRentalDTO saveRental(CreateRentalDTO dto) {

        //VALIDACAO DA MAQUINA SE EXISTE
        //SE ESTA DISPONIVEL PARA ALUGUEL
        Machine machine = validateMachine(dto.machineId());
        checkAvailabilityForRental(machine);

        //VALIDA USUARIO
        //VERIFICA SE EXISTE
        User user = validateUser(dto.userId());

        //CRIA OBJETO RENTAL
        Rental rental = mapper.toEntity(dto);

        //CHAMA METODO PARA CALCULAR O TEMPO DE RENTAL
        rentalCalculator.calculateRentalTime(machine, dto.numberControl(), dto.totalCost(), rental);

        repository.save(rental);

        addRentalForMachine(rental, machine);
        updateAvailableStatusMachine(machine);
        addRentalForUser(rental, user);

        return mapper.toDTO(rental);
    }

    @Transactional
    public CreateRentalDTO patchRental(Long id, RentalPatchDTO patchDTO) {
        Optional<Rental> rentalOptional = Optional.ofNullable(repository.findById(id).orElseThrow(() -> new RentalNotFoundException(id)));
        return mapper.toDTO(updateFields(patchDTO, rentalOptional));
    }

    public CreateRentalDTO getRentalById(Long id) {
        Rental rental = repository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
        return mapper.toDTO(rental);
    }

    public List<CreateRentalDTO> getAllRental() {
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
