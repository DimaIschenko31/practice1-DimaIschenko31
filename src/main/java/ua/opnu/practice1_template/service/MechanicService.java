package ua.opnu.practice1_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.dto.MechanicDto;
import ua.opnu.practice1_template.dto.MechanicStatisticDto;
import ua.opnu.practice1_template.exception.ResourceNotFoundException;
import ua.opnu.practice1_template.model.Mechanic;
import ua.opnu.practice1_template.repository.MechanicRepository;
import ua.opnu.practice1_template.repository.ServiceRecordRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MechanicService {

    private final MechanicRepository mechanicRepository;
    private final ServiceRecordRepository serviceRecordRepository;

    @Autowired
    public MechanicService(MechanicRepository mechanicRepository, ServiceRecordRepository serviceRecordRepository) {
        this.mechanicRepository = mechanicRepository;
        this.serviceRecordRepository = serviceRecordRepository;
    }

    public MechanicDto createMechanic(MechanicDto mechanicDto) {
        Mechanic mechanic = convertToEntity(mechanicDto);
        Mechanic savedMechanic = mechanicRepository.save(mechanic);
        return convertToDto(savedMechanic);
    }

    public List<MechanicDto> getAllMechanics() {
        return mechanicRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MechanicDto getMechanicById(Long id) {
        Mechanic mechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + id));
        return convertToDto(mechanic);
    }

    public MechanicDto updateMechanic(Long id, MechanicDto mechanicDto) {
        Mechanic existingMechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + id));

        existingMechanic.setName(mechanicDto.getName());
        existingMechanic.setSpecialization(mechanicDto.getSpecialization());

        Mechanic updatedMechanic = mechanicRepository.save(existingMechanic);
        return convertToDto(updatedMechanic);
    }

    public void deleteMechanic(Long id) {
        Mechanic mechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + id));
        mechanicRepository.delete(mechanic);
    }

    public MechanicStatisticDto getMechanicStatistics(Long id) {
        Mechanic mechanic = mechanicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mechanic not found with id: " + id));

        Long servicesCount = serviceRecordRepository.countServicesByMechanic(id);

        return new MechanicStatisticDto(
                mechanic.getId(),
                mechanic.getName(),
                servicesCount
        );
    }

    private Mechanic convertToEntity(MechanicDto mechanicDto) {
        Mechanic mechanic = new Mechanic();
        mechanic.setId(mechanicDto.getId());
        mechanic.setName(mechanicDto.getName());
        mechanic.setSpecialization(mechanicDto.getSpecialization());
        return mechanic;
    }

    private MechanicDto convertToDto(Mechanic mechanic) {
        return new MechanicDto(
                mechanic.getId(),
                mechanic.getName(),
                mechanic.getSpecialization()
        );
    }
}