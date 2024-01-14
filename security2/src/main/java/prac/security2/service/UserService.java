package prac.security2.service;

import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prac.security2.domain.User;
import prac.security2.dto.JoinForm;
import prac.security2.dto.LoginForm;
import prac.security2.dto.LoginResponse;
import prac.security2.dto.TokenInfo;
import prac.security2.repository.UserRepository;
import prac.security2.security.JwtTokenProvider;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void signup(JoinForm joinUser) {
        repository.findByUsername(joinUser.getUsername())
                .ifPresent(u -> {
                    throw new DuplicateRequestException("이미 존재하는 회원 아이디입니다.");
                });
        User user = joinUser.toEntity();
        user.encodePassword(passwordEncoder);
        repository.save(user);
    }

    public LoginResponse signin(LoginForm loginForm) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword());

        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authenticate);
        User user = repository.findByUsername(loginForm.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("아이디와 일치하는 회원 정보가 없음"));
        return LoginResponse.ofEntity(user, tokenInfo);
    }
}
