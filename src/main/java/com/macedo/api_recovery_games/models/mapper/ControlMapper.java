package com.macedo.api_recovery_games.models.mapper;

import com.macedo.api_recovery_games.models.Control;
import com.macedo.api_recovery_games.models.dtos.ControlDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ControlMapper {

    ControlDTO toDTO(Control control);

    Control toEntity(ControlDTO controlDTO);

    List<ControlDTO> toDTOList(List<Control> controlsList);

    List<Control> toEntityList(List<ControlDTO> controlDTOList);

}
