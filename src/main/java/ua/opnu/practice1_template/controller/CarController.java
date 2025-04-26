package ua.opnu.practice1_template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/clients/{clientId}/cars")
    public ResponseEntity<Car> addCarToClient(@PathVariable Long clientId, @RequestBody Car car) {
        return ResponseEntity.ok(carService.addCarToClient(clientId, car));
    }

    @GetMapping("/clients/{clientId}/cars")
    public ResponseEntity<List<Car>> getClientCars(@PathVariable Long clientId) {
        return ResponseEntity.ok(carService.getClientCars(clientId));
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.getCarById(id));
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        return ResponseEntity.ok(carService.updateCar(id, car));
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cars/most-serviced")
    public ResponseEntity<List<Car>> getCarsWithMostServiceRecords() {
        return ResponseEntity.ok(carService.getCarsWithMostServiceRecords());
    }
}