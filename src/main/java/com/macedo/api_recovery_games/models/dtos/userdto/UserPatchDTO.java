package com.macedo.api_recovery_games.models.dtos.userdto;

import java.util.Optional;

public record UserPatchDTO(Optional<String> name, Optional<String> phone) {

    public UserPatchDTO {
        name = name != null ? name : Optional.empty();
        phone = phone != null ? phone : Optional.empty();
    }
}
