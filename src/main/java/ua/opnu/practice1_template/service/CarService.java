package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.model.Client;
import ua.opnu.practice1_template.repository.CarRepository;
import ua.opnu.practice1_template.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;

    public Car addCarToClient(Long clientId, Car car) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));
        car.setClient(client);
        return carRepository.save(car);
    }

    public List<Car> getClientCars(Long clientId) {
        return carRepository.findByClientId(clientId);
    }

    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    public Car updateCar(Long id, Car carDetails) {
        Car car = getCarById(id);
        car.setMake(carDetails.getMake());
        car.setModel(carDetails.getModel());
        car.setYear(carDetails.getYear());
        car.setVin(carDetails.getVin());
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<Car> getCarsWithMostServiceRecords() {
        return carRepository.findCarsWithMostServiceRecords();
    }
}