package prac.security2.controller;

import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import prac.security2.dto.JoinForm;
import prac.security2.dto.LoginForm;
import prac.security2.dto.LoginResponse;
import prac.security2.service.UserService;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserService service;

    @GetMapping("/home")
    public String home() {
        return "<h1>home<h1>";
    }

    @ExceptionHandler(DuplicateRequestException.class)
    @PostMapping("/join")
    public String join(@RequestBody JoinForm joinForm) {
        service.signup(joinForm);
        return "회원 가입 완료";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm){
        LoginResponse loginResponse = service.signin(loginForm);
        return loginResponse.toString();
    }
}
