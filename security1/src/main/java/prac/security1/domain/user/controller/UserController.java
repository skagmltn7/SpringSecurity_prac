package prac.security1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import prac.security1.domain.user.domain.User;
import prac.security1.domain.user.dto.JoinForm;
import prac.security1.domain.user.repository.UserRepository;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping({"","/"})
    public String home(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @PostMapping("/login")
    public String login(){
        return "redirect:/";
    }

    @GetMapping("/join")
    public String join(){
        return "join";
    }

    @PostMapping("/join")
    public String join(JoinForm joinUser){
        Optional<User> user = repository.findByUsername(joinUser.getUsername());
        if(user.isPresent()){
            throw new DuplicateKeyException("중복 아이디의 회원 정보 존재");
        }
        repository.save(joinUser.toEntity(passwordEncoder));
        return "redirect:/login";
    }
}
