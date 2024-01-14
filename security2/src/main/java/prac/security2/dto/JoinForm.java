package prac.security2.dto;

import lombok.Data;
import prac.security2.domain.User;

@Data
public class JoinForm {

    private String username;
    private String password;

    public User toEntity() {
        return User.builder()
                .username(getUsername())
                .password(getPassword())
                .roles("USER")
                .build();
    }
}
