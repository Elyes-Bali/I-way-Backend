package com.example.project.backend.services.doctorServ;

import com.example.project.backend.repositories.DoctorAvailabilityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService{
    @Autowired
    private DoctorAvailabilityRepository doctorAvailabilityRepository;
    @Override
    public void deleteAppointment(Long id) {
        doctorAvailabilityRepository.deleteById(id);
    }
}
