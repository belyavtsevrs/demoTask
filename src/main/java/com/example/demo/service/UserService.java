package com.example.demo.service;

import com.example.demo.DTO.CreateUserDTO;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.InvalidDataException;
import com.example.demo.model.Image;
import com.example.demo.model.Users;
import com.example.demo.model.enums.Roles;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.common.AbstractService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class UserService extends AbstractService<Users, UserRepository> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ImageRepository imageRepository;
    public UserService(UserRepository storage, BCryptPasswordEncoder bCryptPasswordEncoder, ImageRepository imageRepository) {
        super(storage);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.imageRepository = imageRepository;
    }
    public Users create(CreateUserDTO createUserDTO) {
            checkDTO(createUserDTO);

            return storage.save(toEntity(createUserDTO));
    }
    @Override
    public List<Users> findAll(){
        return super.findAll();
    }

    public void addImage(Principal principal,MultipartFile multipartFile){
        Users users = storage.findByEmail(principal.getName());
        Image image = toImage(multipartFile);
        users.setAvatar(image);
        imageRepository.save(image);
        storage.save(users);
    }

    private Image toImage(MultipartFile multipartFile){
        try {
            Image image = new Image();
            image.setName(multipartFile.getName());
            image.setOriginalFilename(multipartFile.getOriginalFilename());
            image.setContentType(multipartFile.getContentType());
            image.setSize(multipartFile.getSize());
            image.setData(multipartFile.getBytes());

            return image;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private Users toEntity(CreateUserDTO createUserDTO){
        checkDTO(createUserDTO);
        Users data = new Users();
        data.setEmail(createUserDTO.getEmail().toLowerCase().trim());
        data.setLogin(createUserDTO.getLogin().toLowerCase().trim());
        data.setName(createUserDTO.getName().trim());
        data.setLastName(createUserDTO.getLastName().trim());
        data.setPassword(bCryptPasswordEncoder.encode(createUserDTO.getPassword().trim()));
        data.getRoles().add(Roles.USER);

        return data;
    }
    private void checkDTO(CreateUserDTO createUserDTO) {
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            throw new InvalidDataException("Password not equals");
        }
        if (!(storage.findByEmail(createUserDTO.getEmail()) == null)) {
            throw new AlreadyExistsException("User already exist");
        }
    }
}
