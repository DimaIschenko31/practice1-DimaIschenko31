package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.dto.CarDto;
import ua.opnu.practice1_template.exception.ResourceNotFoundException;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.model.Client;
import ua.opnu.practice1_template.repository.CarRepository;
import ua.opnu.practice1_template.repository.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public CarService(CarRepository carRepository, ClientRepository clientRepository) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
    }

    public CarDto createCar(CarDto carDto) {
        Car car = convertToEntity(carDto);
        Car savedCar = carRepository.save(car);
        return convertToDto(savedCar);
    }

    public List<CarDto> getCarsByClientId(Long clientId) {
        return carRepository.findByClientId(clientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CarDto getCarById(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return convertToDto(car);
    }

    public CarDto updateCar(Long id, CarDto carDto) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        Client client = clientRepository.findById(carDto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + carDto.getClientId()));

        existingCar.setClient(client);
        existingCar.setMake(carDto.getMake());
        existingCar.setModel(carDto.getModel());
        existingCar.setYear(carDto.getYear());
        existingCar.setVin(carDto.getVin());

        Car updatedCar = carRepository.save(existingCar);
        return convertToDto(updatedCar);
    }

    public void deleteCar(Long id) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        carRepository.delete(car);
    }

    private Car convertToEntity(CarDto carDto) {
        Car car = new Car();
        car.setId(carDto.getId());

        if (carDto.getClientId() != null) {
            Client client = clientRepository.findById(carDto.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + carDto.getClientId()));
            car.setClient(client);
        }

        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setYear(carDto.getYear());
        car.setVin(carDto.getVin());
        return car;
    }

    private CarDto convertToDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getClient().getId(),
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.getVin()
        );
    }
}