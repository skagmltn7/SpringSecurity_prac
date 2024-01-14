package prac.security1.domain.user.security.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prac.security1.domain.user.domain.User;
import prac.security1.domain.user.repository.UserRepository;
import prac.security1.domain.user.security.SecurityUser;

import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2KakaoUserService extends DefaultOAuth2UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = super.loadUser(userRequest).getAttributes();
        Map<String, String> kakaoAttributes = (Map<String, String>) attributes.get("kakao_account");
        System.out.println("super.loadUser(userRequest).getAttributes() = " + attributes);
        String username = userRequest.getClientRegistration().getClientName() + "_" + attributes.get("id");
        Optional<User> user = repository.findByUsername(username);
        if (user.isEmpty()) {
            User joinUser = repository.save(User.builder()
                                .username(username)
                                .password(passwordEncoder.encode(username))
                                .email(kakaoAttributes.get("email"))
                                .role("ROLE_USER")
                                .build());
            return new SecurityUser(joinUser, attributes);
        }
        return new SecurityUser(user.get(),attributes);
    }
}
