package prac.security2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prac.security2.domain.User;
import prac.security2.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 아이디로 가입된 유저가 없습니다."));
        return new SecurityUser(findUser);
    }
}
