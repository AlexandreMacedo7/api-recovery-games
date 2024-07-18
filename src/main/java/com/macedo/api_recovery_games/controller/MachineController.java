package com.macedo.api_recovery_games.controller;

import com.macedo.api_recovery_games.models.dtos.MachineDTO;
import com.macedo.api_recovery_games.models.dtos.MachinePatchDTO;
import com.macedo.api_recovery_games.service.MachineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machines")
public class MachineController {
    @Autowired
    private MachineService machineService;

    @PostMapping
    public ResponseEntity<MachineDTO> createMachine(@Valid @RequestBody MachineDTO machineDTO) {
        MachineDTO savedMachine = machineService.saveMachine(machineDTO);
        return ResponseEntity.ok(savedMachine);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MachineDTO> patchMachine(@PathVariable Long id, @RequestBody MachinePatchDTO machinePatchDTO) {
        return ResponseEntity.ok(machineService.patchMachine(id, machinePatchDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineDTO> getMachine(@PathVariable Long id) {
        return ResponseEntity.ok(machineService.getMachineById(id));
    }

    @GetMapping
    public ResponseEntity<List<MachineDTO>> getAllMachines() {
        return ResponseEntity.ok(machineService.getAllMachines());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachineByID(@PathVariable Long id) {
        machineService.deleteMachineById(id);
        return ResponseEntity.noContent().build();
    }
}
