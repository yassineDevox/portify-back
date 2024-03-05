package io.yanocode.portify.resource;

import io.yanocode.portify.Constant.Constant;
import io.yanocode.portify.Domain.User;
import io.yanocode.portify.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.created(URI.create("/users/<user ID>")).body(userService.createUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsers(@PathVariable(value="id")String id){
        return ResponseEntity.ok().body(userService.getUser(id));
    }

    @PutMapping("photo")
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("id") String id,
            @RequestParam("file")MultipartFile file){
        return ResponseEntity.ok().body(userService.uploadPhoto(id,file));
    }
    @GetMapping(path="/image/{filename}")
    public byte[] getPhoto(@PathVariable("filename")String filename) throws IOException{
        return Files.readAllBytes(Paths.get(Constant.PHOTOS_DIRECTORY + filename));
    }
}
