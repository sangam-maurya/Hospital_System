package com.demo.service;


import com.demo.entity.Signup;
import com.demo.payload.LoginDto;
import com.demo.repositery.SignupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignupService {

    private final SignupRepository signupRepository;

    private final JWTService jwtService;

    public SignupService(SignupRepository signupRepository, JWTService jwtService) {
        this.signupRepository = signupRepository;
        this.jwtService = jwtService;
    }

    public Object createSignup(Signup signup){
        Optional<Signup> username = signupRepository.findByUsername(signup.getUsername());
        if (username.isPresent()){
           return "username already present";
        }
        Optional<Signup> email = signupRepository.findByEmail(signup.getEmail());
        if (email.isPresent()){
            return "email is already present";
        }
        String hashpw = BCrypt.hashpw(signup.getPassword(), BCrypt.gensalt(5));
        signup.setPassword(hashpw);
        Signup save = signupRepository.save(signup);
        return save;
    }

    public String verifyLogin(LoginDto loginDto){
        Optional<Signup> username = signupRepository.findByUsername(loginDto.getUsername());
        if (username.isPresent()){
            Signup signup = username.get();

            if (BCrypt.checkpw(loginDto.getPassword() , signup.getPassword())){
                String token = jwtService.generateToken(loginDto.getUsername());
                return token;
            }else {
                return "Password mismatch ";
            }
        }else {
        return "user not found";
        }
    }
}

