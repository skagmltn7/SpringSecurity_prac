package prac.security2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import prac.security2.domain.User;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String name;
    private String accessToken;
    private String refreshToken;
    private String role;

    public static LoginResponse ofEntity(User user, TokenInfo tokenInfo) {
        return new LoginResponse(
                user.getUsername(),
                tokenInfo.getAccessToken(),
                tokenInfo.getRefreshToken(),
                user.getRoles()
        );
    }
}
