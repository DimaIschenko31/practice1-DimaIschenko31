package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.ServiceRecord;
import ua.opnu.practice1_template.service.ServiceRecordService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ServiceRecordController {
    private final ServiceRecordService serviceRecordService;

    @PostMapping("/cars/{carId}/mechanics/{mechanicId}/service-records")
    public ResponseEntity<ServiceRecord> createServiceRecord(
            @PathVariable Long carId,
            @PathVariable Long mechanicId,
            @RequestBody ServiceRecord serviceRecord) {
        return ResponseEntity.ok(serviceRecordService.createServiceRecord(carId, mechanicId, serviceRecord));
    }

    @GetMapping("/cars/{carId}/service-records")
    public ResponseEntity<List<ServiceRecord>> getCarServiceRecords(@PathVariable Long carId) {
        return ResponseEntity.ok(serviceRecordService.getCarServiceRecords(carId));
    }

    @GetMapping("/mechanics/{mechanicId}/service-records")
    public ResponseEntity<List<ServiceRecord>> getMechanicServiceRecords(@PathVariable Long mechanicId) {
        return ResponseEntity.ok(serviceRecordService.getMechanicServiceRecords(mechanicId));
    }

    @GetMapping("/service-records/{id}")
    public ResponseEntity<ServiceRecord> getServiceRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceRecordService.getServiceRecordById(id));
    }

    @PutMapping("/service-records/{id}")
    public ResponseEntity<ServiceRecord> updateServiceRecord(
            @PathVariable Long id,
            @RequestBody ServiceRecord serviceRecord) {
        return ResponseEntity.ok(serviceRecordService.updateServiceRecord(id, serviceRecord));
    }

    @DeleteMapping("/service-records/{id}")
    public ResponseEntity<Void> deleteServiceRecord(@PathVariable Long id) {
        serviceRecordService.deleteServiceRecord(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/service-records/{recordId}/service-types/{typeId}")
    public ResponseEntity<ServiceRecord> assignServiceTypeToRecord(
            @PathVariable Long recordId,
            @PathVariable Long typeId) {
        return ResponseEntity.ok(serviceRecordService.assignServiceTypeToRecord(recordId, typeId));
    }

    @GetMapping("/cars/{carId}/total-service-amount")
    public ResponseEntity<Long> getTotalServiceAmountForCar(@PathVariable Long carId) {
        return ResponseEntity.ok(serviceRecordService.getTotalServiceAmountForCar(carId));
    }

    @GetMapping("/service-records/period")
    public ResponseEntity<List<ServiceRecord>> getServiceRecordsForPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(serviceRecordService.getServiceRecordsForPeriod(startDate, endDate));
    }
}