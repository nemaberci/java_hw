package hu.nemaberci.budgetneptun.service;

import hu.nemaberci.budgetneptun.entity.User;
import hu.nemaberci.budgetneptun.repository.UserRepository;
import java.util.ArrayList;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(String neptunCode, String password) {
        userRepository.save(
                new User().setNeptunCode(neptunCode)
                        .setPasswordHash(passwordEncoder.encode(password))
                        .setRoles(new ArrayList<>())
        );
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
