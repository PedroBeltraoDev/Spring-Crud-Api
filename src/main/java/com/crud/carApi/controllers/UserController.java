package com.crud.carApi.controllers;

import com.crud.carApi.entities.User;
import com.crud.carApi.repositories.UserRepository;
import com.crud.carApi.repositories.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    //Post de um Usuario
    @PostMapping
    public ResponseEntity create(@RequestBody UserRequest UserRequest){
        User user = new User(UserRequest);
        userRepository.save(user);
        return ResponseEntity.ok("Salvo");
    }

    //Get de todos os Users
    @GetMapping
    public ResponseEntity listUser(){
        var user = userRepository.findAll();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return ResponseEntity.ok("Usuário deletado com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable UUID id, @RequestBody UserRequest userRequest) {
        var userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setNome(userRequest.name());
            user.setEmail(userRequest.email());
            userRepository.save(user);
            return ResponseEntity.ok("Usuário atualizado com sucesso.");
        } else {
            return ResponseEntity.status(404).body("Usuário não encontrado.");
        }
    }


}
