package com.macedo.api_recovery_games.models.mapper;

import com.macedo.api_recovery_games.models.Control;
import com.macedo.api_recovery_games.models.dtos.controldto.CreateControlDTO;
import com.macedo.api_recovery_games.models.dtos.controldto.ControlResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ControlMapper {

    ControlResponseDTO toDTO(Control control);

    Control toEntity(CreateControlDTO createControlDTO);

    List<ControlResponseDTO> toDTOList(List<Control> controlsList);

    List<DetailsControlDTO> toDetailsDTOList(List<Control> controlsList);

    List<Control> toEntityList(List<CreateControlDTO> createControlDTOList);

}
