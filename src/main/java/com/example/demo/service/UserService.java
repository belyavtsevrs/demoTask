package com.example.demo.service;

import com.example.demo.DTO.RegistrationDTO;
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

    public Users create(RegistrationDTO registrationDTO) {
            if (!(storage.findByEmail(registrationDTO.getEmail()) == null)) {
                throw new AlreadyExistsException("User already exist");
            }
            Users data = new Users();
            data.setEmail(registrationDTO.getEmail().toLowerCase().trim());
            data.setLogin(registrationDTO.getLogin().toLowerCase().trim());
            data.setName(registrationDTO.getName().toLowerCase().trim());
            data.setLastName(registrationDTO.getLastName().toLowerCase().trim());
            if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
                throw new InvalidDataException("Password not equals");
            }
            data.setPassword(bCryptPasswordEncoder.encode(registrationDTO.getPassword().toLowerCase().trim()));
            data.getRoles().add(Roles.USER);
            return storage.save(data);
    }
    @Override
    public List<Users> findAll(){
        return super.findAll();
    }
    public void addImage(Principal principal,MultipartFile multipartFile){
        Users users = storage.findByEmail(principal.getName());
        imageRepository.save(toImage(multipartFile));
        users.setAvatar(toImage(multipartFile));
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
}
