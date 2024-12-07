package com.demo.controller;

import com.demo.entity.Signup;
import com.demo.payload.LoginDto;
import com.demo.payload.TokenDto;
import com.demo.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SignupController {
    @Autowired
    private SignupService service;

    @PostMapping("/signup")
    public ResponseEntity<?>signup(@RequestBody Signup signup){
        Object serviceSignup = service.createSignup(signup);
        return new ResponseEntity<>(signup , HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> veriFyLogin(@RequestBody LoginDto loginDto){
        String verifyLogin = service.verifyLogin(loginDto);
        if (verifyLogin!=null){
            TokenDto tokenDto = new TokenDto();
            tokenDto.setToken(verifyLogin);
            tokenDto.setType("jwt");
            return new ResponseEntity<>(tokenDto , HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid password" , HttpStatus.BAD_REQUEST);
        }
    }
}
