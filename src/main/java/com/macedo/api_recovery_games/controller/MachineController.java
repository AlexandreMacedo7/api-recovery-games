package com.macedo.api_recovery_games.controller;

import com.macedo.api_recovery_games.models.dtos.controldto.UpdateControlDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.CreateMachineDTO;
import com.macedo.api_recovery_games.models.dtos.machinedto.MachineResponseDTO;
import com.macedo.api_recovery_games.models.dtos.rentaldto.RentalDTO;
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
    public ResponseEntity<MachineResponseDTO> createMachine(@Valid @RequestBody CreateMachineDTO createMachineDTO) {
        MachineResponseDTO savedMachine = machineService.saveMachine(createMachineDTO);
        return ResponseEntity.ok(savedMachine);
    }

    //    @PatchMapping("/{id}")
//    public ResponseEntity<CreateMachineDTO> patchMachine(@PathVariable Long id, @RequestBody MachinePatchDTO machinePatchDTO) {
//        return ResponseEntity.ok();//machineService.patchMachine(id, machinePatchDTO));
//    }
    @PatchMapping("/{id}")
    public ResponseEntity<MachineResponseDTO> patchValueControlMachine(@PathVariable Long id, @RequestBody UpdateControlDTO dto) {
        return ResponseEntity.ok(machineService.updateValueControl(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineResponseDTO> getMachine(@PathVariable Long id) {
        return ResponseEntity.ok(machineService.getMachineById(id));
    }

    @GetMapping("/{id}/rentals")
    public ResponseEntity<List<RentalDTO>> getRentalsByMachineId(@PathVariable Long id) {
        return ResponseEntity.ok(machineService.getRentalsByMachineId(id));
    }

    @GetMapping
    public ResponseEntity<List<CreateMachineDTO>> getAllMachines() {
        return ResponseEntity.ok(machineService.getAllMachines());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMachineByID(@PathVariable Long id) {
        machineService.deleteMachineById(id);
        return ResponseEntity.noContent().build();
    }
}
