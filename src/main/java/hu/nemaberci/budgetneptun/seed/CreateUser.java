package hu.nemaberci.budgetneptun.seed;

import hu.nemaberci.budgetneptun.entity.User;
import hu.nemaberci.budgetneptun.repository.UserRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public CreateUser(UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void seed() {

        userRepository.saveAll(
                List.of(
                        new User()
                                .setNeptunCode("QWE123")
                                .setPasswordHash(passwordEncoder.encode("password"))
                                .setRoles(List.of("ROLE_ADMIN"))
                )
        );

    }

}
