package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.model.Mechanic;
import ua.opnu.practice1_template.model.ServiceRecord;
import ua.opnu.practice1_template.model.ServiceType;
import ua.opnu.practice1_template.repository.CarRepository;
import ua.opnu.practice1_template.repository.MechanicRepository;
import ua.opnu.practice1_template.repository.ServiceRecordRepository;
import ua.opnu.practice1_template.repository.ServiceTypeRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRecordService {
    private final ServiceRecordRepository serviceRecordRepository;
    private final CarRepository carRepository;
    private final MechanicRepository mechanicRepository;
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceRecord createServiceRecord(Long carId, Long mechanicId, ServiceRecord serviceRecord) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + carId));
        Mechanic mechanic = mechanicRepository.findById(mechanicId)
                .orElseThrow(() -> new RuntimeException("Mechanic not found with id: " + mechanicId));

        serviceRecord.setCar(car);
        serviceRecord.setMechanic(mechanic);

        return serviceRecordRepository.save(serviceRecord);
    }

    public List<ServiceRecord> getCarServiceRecords(Long carId) {
        return serviceRecordRepository.findByCarId(carId);
    }

    public List<ServiceRecord> getMechanicServiceRecords(Long mechanicId) {
        return serviceRecordRepository.findByMechanicId(mechanicId);
    }

    public ServiceRecord getServiceRecordById(Long id) {
        return serviceRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service record not found with id: " + id));
    }

    public ServiceRecord updateServiceRecord(Long id, ServiceRecord serviceRecordDetails) {
        ServiceRecord serviceRecord = getServiceRecordById(id);
        serviceRecord.setDate(serviceRecordDetails.getDate());
        serviceRecord.setDescription(serviceRecordDetails.getDescription());
        return serviceRecordRepository.save(serviceRecord);
    }

    public void deleteServiceRecord(Long id) {
        serviceRecordRepository.deleteById(id);
    }

    public ServiceRecord assignServiceTypeToRecord(Long recordId, Long serviceTypeId) {
        ServiceRecord serviceRecord = getServiceRecordById(recordId);
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new RuntimeException("Service type not found with id: " + serviceTypeId));

        serviceRecord.getServiceTypes().add(serviceType);
        return serviceRecordRepository.save(serviceRecord);
    }

    public Long getTotalServiceAmountForCar(Long carId) {
        return serviceRecordRepository.getTotalServiceAmountForCar(carId);
    }

    public List<ServiceRecord> getServiceRecordsForPeriod(LocalDate startDate, LocalDate endDate) {
        return serviceRecordRepository.findByDateBetween(startDate, endDate);
    }
}