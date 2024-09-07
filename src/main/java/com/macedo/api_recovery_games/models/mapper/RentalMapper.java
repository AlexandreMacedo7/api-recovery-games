package com.macedo.api_recovery_games.models.mapper;

import com.macedo.api_recovery_games.models.Rental;
import com.macedo.api_recovery_games.models.dtos.rentaldto.CreateRentalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RentalMapper {

    @Mapping(source = "machine.id", target = "machineId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "totalCost", target = "totalCost")
    CreateRentalDTO toDTO(Rental rental);

    @Mapping(source = "machineId", target = "machine.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "totalCost", target = "totalCost")
    Rental toEntity(CreateRentalDTO createRentalDTO);

    List<CreateRentalDTO> toDTOList(List<Rental> rentalList);

    List<Rental> toEntityList(List<CreateRentalDTO> createRentalDTOS);
}
