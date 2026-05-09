package com.lucas.spring_security_studies.resources;

import com.lucas.spring_security_studies.config.security.TokenService;
import com.lucas.spring_security_studies.dto.AuthenticationDto;
import com.lucas.spring_security_studies.dto.LoginResponseDto;
import com.lucas.spring_security_studies.dto.UserRequestDto;
import com.lucas.spring_security_studies.entities.User;
import com.lucas.spring_security_studies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationResource {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody UserRequestDto data) {
        System.out.println("chegou no register");
        if(this.userRepository.findByEmail(data.getEmail()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
        User newUser = new User(data.getEmail(), encryptedPassword, data.getRole());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
