package com.devsenior.vet_turno.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        System.out.println("user (password 1234) → " + encoder.encode("1234"));
        System.out.println("admin (password 1234) → " + encoder.encode("1234"));
        System.out.println("vet (password 1234) → " + encoder.encode("1234"));
    }
}
