package com.example.library_management_system.service.serviceImpl;

import com.example.library_management_system.config.JwtService;
import com.example.library_management_system.config.PasswordConfig;
import com.example.library_management_system.dto.AuthenticationResponse;
import com.example.library_management_system.dto.LoginDto;
import com.example.library_management_system.dto.SignupDto;
import com.example.library_management_system.dto.UpdatePatronRequest;
import com.example.library_management_system.entity.User;
import com.example.library_management_system.exception.BadRequestException;
import com.example.library_management_system.exception.UserAlreadyExistException;
import com.example.library_management_system.exception.UserNotFoundException;
import com.example.library_management_system.repository.UserRepository;
import com.example.library_management_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
        private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
        private final JwtService jwtService;
        private final PasswordConfig passwordConfig;

        @Override
        public ResponseEntity<User> registerPatron(SignupDto signupDto){
            if (!passwordConfig.validatePassword(signupDto.getPassword())) {
                throw new BadRequestException("Invalid password format", HttpStatus.BAD_REQUEST);
            }
            if(userRepository.existsByUsername(signupDto.getEmail())){
                throw new UserAlreadyExistException("Username already exists", HttpStatus.BAD_REQUEST);
            }

            User patron = new User();
            patron.setName(signupDto.getName());
            patron.setUsername(signupDto.getEmail());
            patron.setAddress(signupDto.getAddress());
            patron.setPassword(passwordConfig.passwordEncoder().encode(signupDto.getPassword()));
            patron.setPhoneNumber(signupDto.getPhoneNumber());

            userRepository.save(patron);

            return ResponseEntity.status(HttpStatus.OK).body(patron);
        }

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid email or password");
        }

        User users = userRepository.findByUsernameIgnoreCase(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + request.getEmail(), HttpStatus.NOT_FOUND));


        String jwtToken = jwtService.generateToken((UserDetails) users);

        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtToken);
        response.setUser(users);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Override
    public User getUserById(Long patronId){
            return userRepository.findById(patronId)
                    .orElseThrow(() -> new UserNotFoundException("user not found", HttpStatus.NOT_FOUND));
    }
    @Override
    public List<User> getAllPatron(){
            return userRepository.findAll();
    }
    @Override
    public ResponseEntity<User> updatePatron(UpdatePatronRequest request){
          User user = getCurrentUser();

          user.setPhoneNumber(request.getPhoneNumber());
          user.setAddress(request.getAddress());
          user.setName(request.getName());

          userRepository.save(user);

          return ResponseEntity.ok(user);
    }
    @Override
    public ResponseEntity<String> deletePatronById(Long patronId) {
            if (!userRepository.existsById(patronId)) {
                return ResponseEntity.notFound().build();
            }
            userRepository.deleteById(patronId);
            return ResponseEntity.status(200).body("Patron successfully removed");
        }

        public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (User) authentication.getPrincipal();
        } else {
            throw new BadRequestException("Authenticated user not found", HttpStatus.FORBIDDEN);
        }
    }}
