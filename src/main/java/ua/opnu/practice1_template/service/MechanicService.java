package ua.opnu.practice1_template.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.opnu.practice1_template.model.Mechanic;
import ua.opnu.practice1_template.model.ServiceRecord;
import ua.opnu.practice1_template.repository.MechanicRepository;
import ua.opnu.practice1_template.repository.ServiceRecordRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MechanicService {
    private final MechanicRepository mechanicRepository;
    private final ServiceRecordRepository serviceRecordRepository;

    public Mechanic createMechanic(Mechanic mechanic) {
        return mechanicRepository.save(mechanic);
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicRepository.findAll();
    }

    public Mechanic getMechanicById(Long id) {
        return mechanicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mechanic not found with id: " + id));
    }

    public Mechanic updateMechanic(Long id, Mechanic mechanicDetails) {
        Mechanic mechanic = getMechanicById(id);
        mechanic.setName(mechanicDetails.getName());
        mechanic.setSpecialization(mechanicDetails.getSpecialization());
        return mechanicRepository.save(mechanic);
    }

    public void deleteMechanic(Long id) {
        mechanicRepository.deleteById(id);
    }

    public Map<String, Object> getMechanicStatistics(Long mechanicId) {
        List<ServiceRecord> records = serviceRecordRepository.findByMechanicId(mechanicId);
        Map<String, Object> statistics = new HashMap<>();

        statistics.put("totalServices", records.size());
        statistics.put("serviceRecords", records);

        return statistics;
    }
}