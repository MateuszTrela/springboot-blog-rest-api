package com.springboot.blog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody LoginDto loginDto) throws NoSuchAlgorithmException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String usernameOrEmail = userDetails.getUsername();
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail));

//        user.getId();
//        user.getName();
//        user.getUsername();
//        user.getEmail();

        String token = jwtTokenProvider.generateToken(authentication);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", user.getId());
        userMap.put("username", user.getUsername());
        userMap.put("email", user.getEmail());

        response.put("User", userMap);

        return ResponseEntity.ok(response);


//        return ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpDto signUpDto) throws JsonProcessingException {
        if(userRepository.existsByUsername(signUpDto.getUsername())){
//            return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);

            String errorMessage = "Username is already taken";
            ObjectMapper objectMapper = new ObjectMapper();
            String response = objectMapper.writeValueAsString(Map.of("username", errorMessage));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if(userRepository.existsByEmail(signUpDto.getEmail())){
//            return new ResponseEntity<>("Email is already taken.", HttpStatus.BAD_REQUEST);

            String errorMessage = "Email is already taken";
            ObjectMapper objectMapper = new ObjectMapper();
            String response = objectMapper.writeValueAsString(Map.of("email", errorMessage));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        user.setPosts(new HashSet<>());

        userRepository.save(user);

        return new ResponseEntity<>("User register successfully", HttpStatus.OK);
    }


}
