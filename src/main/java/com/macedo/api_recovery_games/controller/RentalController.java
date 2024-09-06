package com.macedo.api_recovery_games.controller;

import com.macedo.api_recovery_games.models.dtos.rentaldto.RentalDTO;
import com.macedo.api_recovery_games.models.dtos.rentaldto.RentalPatchDTO;
import com.macedo.api_recovery_games.service.RentalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

//    @PostMapping
//    public ResponseEntity<RentalDTO> createRental(@RequestBody @Valid RentalDTO rentalDTO) {
//        return ResponseEntity.ok(rentalService.saveRental(rentalDTO));
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @RequestBody RentalPatchDTO patchDTO) {
        return ResponseEntity.ok(rentalService.patchRental(id, patchDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> getRental(@PathVariable Long id) {
        return ResponseEntity.ok(rentalService.getRentalById(id));
    }

    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRental());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        rentalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
