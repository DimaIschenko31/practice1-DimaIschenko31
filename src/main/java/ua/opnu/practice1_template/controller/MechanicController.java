package ua.opnu.practice1_template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.dto.MechanicDto;
import ua.opnu.practice1_template.dto.MechanicStatisticDto;
import ua.opnu.practice1_template.service.MechanicService;

import java.util.List;

@RestController
@RequestMapping("/api/mechanics")
public class MechanicController {

    private final MechanicService mechanicService;

    @Autowired
    public MechanicController(MechanicService mechanicService) {
        this.mechanicService = mechanicService;
    }

    @PostMapping
    public ResponseEntity<MechanicDto> createMechanic(@RequestBody MechanicDto mechanicDto) {
        MechanicDto createdMechanic = mechanicService.createMechanic(mechanicDto);
        return new ResponseEntity<>(createdMechanic, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MechanicDto>> getAllMechanics() {
        List<MechanicDto> mechanics = mechanicService.getAllMechanics();
        return ResponseEntity.ok(mechanics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MechanicDto> getMechanicById(@PathVariable Long id) {
        MechanicDto mechanic = mechanicService.getMechanicById(id);
        return ResponseEntity.ok(mechanic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MechanicDto> updateMechanic(@PathVariable Long id, @RequestBody MechanicDto mechanicDto) {
        MechanicDto updatedMechanic = mechanicService.updateMechanic(id, mechanicDto);
        return ResponseEntity.ok(updatedMechanic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMechanic(@PathVariable Long id) {
        mechanicService.deleteMechanic(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<MechanicStatisticDto> getMechanicStatistics(@PathVariable Long id) {
        MechanicStatisticDto statistics = mechanicService.getMechanicStatistics(id);
        return ResponseEntity.ok(statistics);
    }
}