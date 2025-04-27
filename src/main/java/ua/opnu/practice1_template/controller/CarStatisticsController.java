package ua.opnu.practice1_template.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.opnu.practice1_template.dto.CarDto;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.repository.ServiceRecordRepository;
import ua.opnu.practice1_template.service.CarService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class CarStatisticsController {

    private final ServiceRecordRepository serviceRecordRepository;
    private final CarService carService;

    @Autowired
    public CarStatisticsController(ServiceRecordRepository serviceRecordRepository, CarService carService) {
        this.serviceRecordRepository = serviceRecordRepository;
        this.carService = carService;
    }

    // 25. Отримати автомобілі з найчастішим обслуговуванням
    @GetMapping("/cars/most-serviced")
    public ResponseEntity<List<Object>> getCarsWithMostServices() {
        List<Object[]> results = serviceRecordRepository.findCarsWithMostServices();
        List<Object> response = new ArrayList<>();

        for (Object[] result : results) {
            Car car = (Car) result[0];
            Long count = (Long) result[1];

            CarDto carDto = carService.getCarById(car.getId());

            response.add(new Object[] {carDto, count});
        }

        return ResponseEntity.ok(response);
    }
}