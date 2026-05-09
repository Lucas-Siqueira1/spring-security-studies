package com.lucas.spring_security_studies.resources;

import com.lucas.spring_security_studies.dto.UserRequestDto;
import com.lucas.spring_security_studies.dto.UserResponseDto;
import com.lucas.spring_security_studies.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUsers() {
        List<UserResponseDto> obj = userService.findAllUsers();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Integer id) {
        UserResponseDto obj = userService.findUserById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> insert(@RequestBody UserRequestDto obj) {
        UserResponseDto saved = userService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Integer id, @RequestBody UserRequestDto newUser) {
        UserResponseDto oldUser = userService.update(id, newUser);
        return ResponseEntity.ok().body(oldUser);
    }

}
