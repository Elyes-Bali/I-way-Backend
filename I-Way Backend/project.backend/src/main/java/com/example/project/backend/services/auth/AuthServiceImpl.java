package com.example.project.backend.services.auth;

import com.example.project.backend.configurations.CloudinaryService;
import com.example.project.backend.dto.SignupRequest;
import com.example.project.backend.dto.UserDto;
import com.example.project.backend.entity.DoctorAvailability;
import com.example.project.backend.entity.User;
import com.example.project.backend.enums.UserRole;
import com.example.project.backend.repositories.UserRepository;
import com.example.project.backend.services.mailing.MailService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MailService mailService;


    public UserDto createUser(SignupRequest signupRequest){
        User user = new User();

        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setRole(signupRequest.getUserRole() != null ? signupRequest.getUserRole() : UserRole.PATIENT);

        if (signupRequest.getUserRole() == UserRole.DOCTOR) {
            user.setVerified(false);
            user.setMatricule(signupRequest.getMatricule());
            Set<DoctorAvailability> availabilities = signupRequest.getAvailabilities().stream().map(dto -> {
                DoctorAvailability availability = new DoctorAvailability();
                availability.setDayOfWeek(dto.getDayOfWeek());
                availability.setStartTime(dto.getStartTime());
                availability.setEndTime(dto.getEndTime());
                availability.setDoctor(user);
                return availability;
            }).collect(Collectors.toSet());
            user.setAvailabilities(availabilities);
        }else if (signupRequest.getUserRole() == UserRole.PHARMACY) {
            user.setVerified(false);
            user.setMatricule(signupRequest.getMatricule());
        }else {
            user.setVerified(true);
        }

        User createdUser = userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        return userDto;
    }

    public Boolean hasUserWithEmail(String email){
        return  userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (null == adminAccount){
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("admin");
            user.setRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
        }
    }

    public UserDto updateUser(Long userId, String name, String email, String education, String experience, String statement, String skills, String address, Long number,String eAddress,String speciality,String matricule, Boolean verified, MultipartFile image,MultipartFile signature, LocalDate birthDate) throws IOException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        Boolean wasPreviouslyVerified = user.isVerified();

        if (name != null) user.setName(name);
        if (email != null) user.setEmail(email);
        if (education != null) user.setEducation(education);
        if (experience != null) user.setExperience(experience);
        if (statement != null) user.setStatement(statement);
        if (skills != null) user.setSkills(skills);
        if (address != null) user.setAdress(address);
        if (number != null) user.setNumber(number);
        if (eAddress != null) user.setEAddress(eAddress);
        if (speciality != null) user.setSpeciality(speciality);
        if (matricule != null) user.setMatricule(matricule);
        if (verified != null) {
            user.setVerified(verified);  // Boolean value will directly update the field
        }
        // Handle image upload
        if (image != null && !image.isEmpty()) {
            // Convert MultipartFile to a byte array
            byte[] imageBytes = image.getBytes();
            String imageUrl = cloudinaryService.uploadImage(imageBytes);
            user.setImgUrl(imageUrl);  // Save the image URL
        }

        if (signature != null && !signature.isEmpty()) {
            // Convert MultipartFile to a byte array
            byte[] signatureBytes = signature.getBytes();
            String signatureUrl = cloudinaryService.uploadImage(signatureBytes);
            user.setSignatureUrl(signatureUrl);  // Save the image URL
        }
        if (birthDate != null) user.setBirthDate(birthDate);
        userRepository.save(user);

        if (user.getRole() == UserRole.DOCTOR && !wasPreviouslyVerified && verified != null && verified) {
            mailService.sendDoctorVerificationEmail(user.getEmail(), user.getName());
        }else if (user.getRole() == UserRole.PHARMACY && !wasPreviouslyVerified && verified != null && verified) {
            mailService.sendPharmacyVerificationEmail(user.getEmail(), user.getName());
        }

        // Convert to UserDto and return
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setVerified(user.isVerified());
        return userDto;
    }

    @Override
    public User getUserbyId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllDoctors() {
        return userRepository.findAllByRole(UserRole.DOCTOR);
    }

    @Override
    public List<User> getAllPatients() {
        return userRepository.findAllByRole(UserRole.PATIENT);
    }

    @Override
    public List<User> getAllPharmacy() {
        return userRepository.findAllByRole(UserRole.PHARMACY);
    }
}
