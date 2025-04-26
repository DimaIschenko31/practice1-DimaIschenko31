package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.ServiceType;
import ua.opnu.practice1_template.service.ServiceTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/service-types")
@RequiredArgsConstructor
public class ServiceTypeController {
    private final ServiceTypeService serviceTypeService;

    @PostMapping
    public ResponseEntity<ServiceType> createServiceType(@RequestBody ServiceType serviceType) {
        return ResponseEntity.ok(serviceTypeService.createServiceType(serviceType));
    }

    @GetMapping
    public ResponseEntity<List<ServiceType>> getAllServiceTypes() {
        return ResponseEntity.ok(serviceTypeService.getAllServiceTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceType> getServiceTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceTypeService.getServiceTypeById(id));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ServiceType>> getMostPopularServiceTypes() {
        return ResponseEntity.ok(serviceTypeService.getMostPopularServiceTypes());
    }
}