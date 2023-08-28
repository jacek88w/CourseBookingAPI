package pl.jacek.coursebookingresolving.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jacek.coursebookingresolving.entity.User;
import pl.jacek.coursebookingresolving.dto.UserDTO;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserTestController {

    private final UserTestService userService;

    public UserTestController(UserTestService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHelloTest() {
        return ResponseEntity.ok("Hello from secure endpoint");
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
