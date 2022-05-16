package hu.nemaberci.budgetneptun.service;

import hu.nemaberci.budgetneptun.dto.UserDTO;
import hu.nemaberci.budgetneptun.entity.UserEntity;
import hu.nemaberci.budgetneptun.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public final static String roleTeacher = "ROLE_TEACHER";
    public final static String roleStudent = "ROLE_STUDENT";

    public UserService(UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(String neptunCode, String password) {

        if (!neptunCode.matches("^[A-Za-z0-9]{6}$")) {
            throw new IllegalArgumentException(
                    "Neptun code is not of valid format (6 numbers / letters)");
        }

        userRepository.save(
                new UserEntity().setNeptunCode(neptunCode)
                        .setPasswordHash(passwordEncoder.encode(password))
                        .setRoles(new ArrayList<>())
        );
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void addTeacherRole(Long id) {
        final var user = userRepository.getById(id);
        user.getRoles().add(roleTeacher);
        userRepository.save(user);
    }

    public void removeTeacherRole(Long id) {
        final var user = userRepository.getById(id);
        user.getRoles().remove(roleTeacher);
        userRepository.save(user);
    }

    public void addStudentRole(Long id) {
        final var user = userRepository.getById(id);
        user.getRoles().add(roleStudent);
        userRepository.save(user);
    }

    public void removeStudentRole(Long id) {
        final var user = userRepository.getById(id);
        user.getRoles().remove(roleStudent);
        userRepository.save(user);
    }

    public UserEntity getStudent(Long id) throws IllegalArgumentException {
        final var user = userRepository.getById(id);
        if (!user.getRoles().contains(roleStudent)) {
            throw new IllegalArgumentException("The provided user is not a student");
        }
        return user;
    }

    public UserEntity getTeacher(Long id) throws IllegalArgumentException {
        final var user = userRepository.getById(id);
        if (!user.getRoles().contains(roleTeacher)) {
            throw new IllegalArgumentException("The provided user is not a teacher");
        }
        return user;
    }

    public Long currentUserId() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            var username = ((UserDetails) principal).getUsername();
            var user = userRepository.findByNeptunCode(username);
            if (user.isEmpty()) {
                return null;
            }
            return user.get().getId();
        } else {
            return null;
        }
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    public List<UserDTO> getStudents() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(roleStudent))
                .map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    public List<UserDTO> getTeachers() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().contains(roleTeacher))
                .map(UserDTO::fromEntity).collect(Collectors.toList());
    }



}
