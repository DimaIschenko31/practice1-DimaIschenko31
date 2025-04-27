package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.dto.ServiceRecordDto;
import ua.opnu.practice1_template.exception.ResourceNotFoundException;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.model.Mechanic;
import ua.opnu.practice1_template.model.ServiceRecord;
import ua.opnu.practice1_template.model.ServiceType;
import ua.opnu.practice1_template.repository.CarRepository;
import ua.opnu.practice1_template.repository.MechanicRepository;
import ua.opnu.practice1_template.repository.ServiceRecordRepository;
import ua.opnu.practice1_template.repository.ServiceTypeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceRecordService {

    private final ServiceRecordRepository serviceRecordRepository;
    private final CarRepository carRepository;
    private final MechanicRepository mechanicRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    @Autowired
    public ServiceRecordService(
            ServiceRecordRepository serviceRecordRepository,
            CarRepository carRepository,
            MechanicRepository mechanicRepository,
            ServiceTypeRepository serviceTypeRepository) {
        this.serviceRecordRepository = serviceRecordRepository;
        this.carRepository = carRepository;
        this.mechanicRepository = mechanicRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    public ServiceRecordDto createServiceRecord(ServiceRecordDto serviceRecordDto) {
        ServiceRecord serviceRecord = convertToEntity(serviceRecordDto);
        ServiceRecord savedServiceRecord = serviceRecordRepository.save(serviceRecord);
        return convertToDto(savedServiceRecord);
    }

    public List<ServiceRecordDto> getServiceRecordsByCarId(Long carId) {
        return serviceRecordRepository.findByCarId(carId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ServiceRecordDto> getServiceRecordsByMechanicId(Long mechanicId) {
        return serviceRecordRepository.findByMechanicId(mechanicId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ServiceRecordDto getServiceRecordById(Long id) {
        ServiceRecord serviceRecord = serviceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service record not found with id: " + id));
        return convertToDto(serviceRecord);
    }

    public ServiceRecordDto updateServiceRecord(Long id, ServiceRecordDto serviceRecordDto) {
        ServiceRecord existingServiceRecord = serviceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service record not found with id: " + id));

        Car car = carRepository.findById(serviceRecordDto.getCarId())
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + serviceRecordDto.getCarId()));

        Mechanic mechanic = mechanicRepository.findById(serviceRecordDto.getMechanicId())
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + serviceRecordDto.getMechanicId()));

        List<ServiceType> serviceTypes = new ArrayList<>();
        for (Long serviceTypeId : serviceRecordDto.getServiceTypeIds()) {
            ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Service type not found with id: " + serviceTypeId));
            serviceTypes.add(serviceType);
        }

        existingServiceRecord.setCar(car);
        existingServiceRecord.setMechanic(mechanic);
        existingServiceRecord.setDate(serviceRecordDto.getDate());
        existingServiceRecord.setDescription(serviceRecordDto.getDescription());
        existingServiceRecord.setServiceTypes(serviceTypes);

        ServiceRecord updatedServiceRecord = serviceRecordRepository.save(existingServiceRecord);
        return convertToDto(updatedServiceRecord);
    }

    public void deleteServiceRecord(Long id) {
        ServiceRecord serviceRecord = serviceRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service record not found with id: " + id));
        serviceRecordRepository.delete(serviceRecord);
    }

    public void assignServiceTypeToRecord(Long recordId, Long serviceTypeId) {
        ServiceRecord serviceRecord = serviceRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Service record not found with id: " + recordId));

        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Service type not found with id: " + serviceTypeId));

        if (serviceRecord.getServiceTypes() == null) {
            serviceRecord.setServiceTypes(new ArrayList<>());
        }

        serviceRecord.getServiceTypes().add(serviceType);
        serviceRecordRepository.save(serviceRecord);
    }

    public BigDecimal getTotalServiceAmountForCar(Long carId) {
        carRepository.findById(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + carId));
        return serviceRecordRepository.getTotalServiceAmountForCar(carId);
    }

    public List<ServiceRecordDto> getServiceRecordsByDateRange(LocalDate startDate, LocalDate endDate) {
        return serviceRecordRepository.findByDateBetween(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ServiceRecord convertToEntity(ServiceRecordDto serviceRecordDto) {
        ServiceRecord serviceRecord = new ServiceRecord();
        serviceRecord.setId(serviceRecordDto.getId());

        if (serviceRecordDto.getCarId() != null) {
            Car car = carRepository.findById(serviceRecordDto.getCarId())
                    .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + serviceRecordDto.getCarId()));
            serviceRecord.setCar(car);
        }

        if (serviceRecordDto.getMechanicId() != null) {
            Mechanic mechanic = mechanicRepository.findById(serviceRecordDto.getMechanicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + serviceRecordDto.getMechanicId()));
            serviceRecord.setMechanic(mechanic);
        }

        serviceRecord.setDate(serviceRecordDto.getDate());
        serviceRecord.setDescription(serviceRecordDto.getDescription());

        List<ServiceType> serviceTypes = new ArrayList<>();
        if (serviceRecordDto.getServiceTypeIds() != null) {
            for (Long serviceTypeId : serviceRecordDto.getServiceTypeIds()) {
                ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                        .orElseThrow(() -> new ResourceNotFoundException("Service type not found with id: " + serviceTypeId));
                serviceTypes.add(serviceType);
            }
        }
        serviceRecord.setServiceTypes(serviceTypes);

        return serviceRecord;
    }

    private ServiceRecordDto convertToDto(ServiceRecord serviceRecord) {
        List<Long> serviceTypeIds = serviceRecord.getServiceTypes() != null ?
                serviceRecord.getServiceTypes().stream()
                        .map(ServiceType::getId)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return new ServiceRecordDto(
                serviceRecord.getId(),
                serviceRecord.getCar().getId(),
                serviceRecord.getMechanic().getId(),
                serviceRecord.getDate(),
                serviceRecord.getDescription(),
                serviceTypeIds
        );
    }
}