package com.macedo.api_recovery_games.controller;

import com.macedo.api_recovery_games.models.dtos.rentaldto.CreateRentalDTO;
import com.macedo.api_recovery_games.models.dtos.userdto.UserDTO;
import com.macedo.api_recovery_games.models.dtos.userdto.UserPatchDTO;
import com.macedo.api_recovery_games.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(service.saveUser(userDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserPatchDTO userPatchDTO) {
        return ResponseEntity.ok(service.patchUser(id, userPatchDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @GetMapping("/{id}/rentals")
    public ResponseEntity<List<CreateRentalDTO>> getRentalsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRentalByUserId(id));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserByID(@PathVariable Long id) {
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
