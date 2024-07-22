package com.macedo.api_recovery_games.controller;

import com.macedo.api_recovery_games.models.dtos.RentalDTO;
import com.macedo.api_recovery_games.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalDTO> createRental(@RequestBody @Valid RentalDTO rentalDTO) {
        return ResponseEntity.ok(rentalService.saveRental(rentalDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }
}
