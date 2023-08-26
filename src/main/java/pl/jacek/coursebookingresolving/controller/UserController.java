package pl.jacek.coursebookingresolving.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.repository.dto.UserDTO;
import pl.jacek.coursebookingresolving.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody UserDTO userDTO, @PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.update(userDTO, id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteById(id),
                HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<String> deleteAll() {
        return new ResponseEntity<>(userService.deleteAll(),
                HttpStatus.OK);
    }
}
