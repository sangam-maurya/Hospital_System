package com.demo.repositery;

import com.demo.entity.Signup;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SignupRepository extends JpaRepository<Signup, Long> {
   Optional<Signup> findByUsername(String username);
   Optional<Signup> findByEmail(String email);
}