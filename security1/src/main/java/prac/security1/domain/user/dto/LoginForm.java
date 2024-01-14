package prac.security1.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import prac.security1.domain.user.domain.User;

@Data
@Builder
public class LoginForm {
    private String username;
    private String password;
    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
