package io.yanocode.portify.Service;

import io.yanocode.portify.Constant.Constant;
import io.yanocode.portify.Domain.User;
import io.yanocode.portify.Repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    public User getUser(String id){
        return userRepo.findById(id).orElseThrow(
                ()-> new RuntimeException("User not Found")
        );
    }
    public User createUser(User user){
        return userRepo.save(user);
    }
    public void deleteUser(User user){
        userRepo.delete(user);
    }
    public String uploadPhoto(String id, MultipartFile file){
        User user = getUser(id);
        String avatarUrl = avatarFunction.apply(id,file);
        user.setPhotoUrl(avatarUrl);
        userRepo.save(user);
        return avatarUrl;
    }

    private final Function<String,String> fileExtension =
            filename-> Optional
                    .of(filename)
                    .filter(name->name.contains("."))
                    .map(name->"."+name.substring(
                            filename
                                    .lastIndexOf(".")+1))
                    .orElse(".png");
    private final BiFunction<String, MultipartFile, String> avatarFunction = (id,image)->{
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(Constant.PHOTOS_DIRECTORY)
                    .toAbsolutePath().normalize();
            if(!Files.exists(fileStorageLocation)){
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(),fileStorageLocation.resolve(filename),REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/users/pics/" + filename)
                    .toUriString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to save the image");
        }
    };
}
