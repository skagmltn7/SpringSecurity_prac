package prac.security1.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import prac.security1.domain.user.domain.User;

@Data
@Builder
public class JoinForm {

    private String username;
    private String password;
    private String email;

    public User toEntity(PasswordEncoder passwordEncoder){
        return User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .role("ROLE_USER")
                .build();
    }

}
