package com.example.alura.ForoHub.controll;

import com.example.alura.ForoHub.jwt.JwtService;
import com.example.alura.ForoHub.model.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paghttp.request.LoginRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginControll {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        User user = (User) userDetailsService.loadUserByUsername(loginRequest.getUsername());
        if (user == null) {
            return ResponseEntity.badRequest().body("This username does not exist");
        }
        Authentication authReq = UsernamePasswordAuthenticationToken
                .authenticated(loginRequest.getUsername(),loginRequest.getPassword(),null);
        this.authenticationManager.authenticate(authReq).getCredentials();
        String token = jwtService.genToken(user.getUsername());
        List<String> roles = user.getAuthorities().stream().map(a -> a.getAuthority()).toList();
        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "username", user.getUsername(),
                        "roles", roles
                )
        );
    }
}