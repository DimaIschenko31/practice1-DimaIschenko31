package ua.opnu.practice1_template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.dto.ServiceRecordDto;
import ua.opnu.practice1_template.service.ServiceRecordService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/service-records")
public class ServiceRecordController {

    private final ServiceRecordService serviceRecordService;

    @Autowired
    public ServiceRecordController(ServiceRecordService serviceRecordService) {
        this.serviceRecordService = serviceRecordService;
    }

    @PostMapping
    public ResponseEntity<ServiceRecordDto> createServiceRecord(@RequestBody ServiceRecordDto serviceRecordDto) {
        ServiceRecordDto createdServiceRecord = serviceRecordService.createServiceRecord(serviceRecordDto);
        return new ResponseEntity<>(createdServiceRecord, HttpStatus.CREATED);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<ServiceRecordDto>> getServiceRecordsByCarId(@PathVariable Long carId) {
        List<ServiceRecordDto> serviceRecords = serviceRecordService.getServiceRecordsByCarId(carId);
        return ResponseEntity.ok(serviceRecords);
    }

    @GetMapping("/mechanic/{mechanicId}")
    public ResponseEntity<List<ServiceRecordDto>> getServiceRecordsByMechanicId(@PathVariable Long mechanicId) {
        List<ServiceRecordDto> serviceRecords = serviceRecordService.getServiceRecordsByMechanicId(mechanicId);
        return ResponseEntity.ok(serviceRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceRecordDto> getServiceRecordById(@PathVariable Long id) {
        ServiceRecordDto serviceRecord = serviceRecordService.getServiceRecordById(id);
        return ResponseEntity.ok(serviceRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceRecordDto> updateServiceRecord(@PathVariable Long id, @RequestBody ServiceRecordDto serviceRecordDto) {
        ServiceRecordDto updatedServiceRecord = serviceRecordService.updateServiceRecord(id, serviceRecordDto);
        return ResponseEntity.ok(updatedServiceRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceRecord(@PathVariable Long id) {
        serviceRecordService.deleteServiceRecord(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{recordId}/service-types/{typeId}")
    public ResponseEntity<Void> assignServiceTypeToRecord(@PathVariable Long recordId, @PathVariable Long typeId) {
        serviceRecordService.assignServiceTypeToRecord(recordId, typeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/car/{carId}/total-amount")
    public ResponseEntity<BigDecimal> getTotalServiceAmountForCar(@PathVariable Long carId) {
        BigDecimal totalAmount = serviceRecordService.getTotalServiceAmountForCar(carId);
        return ResponseEntity.ok(totalAmount);
    }

    @GetMapping("/period")
    public ResponseEntity<List<ServiceRecordDto>> getServiceRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ServiceRecordDto> serviceRecords = serviceRecordService.getServiceRecordsByDateRange(startDate, endDate);
        return ResponseEntity.ok(serviceRecords);
    }
}