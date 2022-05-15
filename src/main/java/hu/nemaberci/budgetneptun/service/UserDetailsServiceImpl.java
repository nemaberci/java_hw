package hu.nemaberci.budgetneptun.service;

import hu.nemaberci.budgetneptun.configuration.CustomUserDetails;
import hu.nemaberci.budgetneptun.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var user = userRepository.findByNeptunCode(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new CustomUserDetails(user.get());
    }
}
