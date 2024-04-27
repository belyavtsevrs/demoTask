package com.example.demo.service;

import com.example.demo.DTO.CreateUserDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.InvalidDataException;
import com.example.demo.model.Image;
import com.example.demo.model.Users;
import com.example.demo.model.enums.Roles;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.common.AbstractService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
public class UserService extends AbstractService<Users, UserRepository> {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final ImageRepository imageRepository;
    public UserService(UserRepository storage, BCryptPasswordEncoder bCryptPasswordEncoder, DaoAuthenticationProvider daoAuthenticationProvider, ImageRepository imageRepository) {
        super(storage);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.imageRepository = imageRepository;
    }
    public Users create(CreateUserDTO createUserDTO) {
            return storage.save(toEntity(createUserDTO));
    }
    public ResponseEntity<?> login(UserDTO userDTO){
        checkAuth(userDTO);
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword());
        try {
            this.daoAuthenticationProvider.authenticate(authenticationRequest);
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public List<Users> findAll(){
        return super.findAll();
    }

    public List<UserDTO> allDTO(){
        return findAll().stream().map(x->toDTO(x)).toList();
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
    private UserDTO toDTO(Users user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setLogin(user.getLogin());
        userDTO.setName(user.getName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDTO;
    }
    private Users toEntity(CreateUserDTO createUserDTO){
        checkRegisterDTO(createUserDTO);
        Users data = new Users();
        data.setEmail(createUserDTO.getEmail().toLowerCase().trim());
        data.setLogin(createUserDTO.getLogin().toLowerCase().trim());
        data.setName(createUserDTO.getName().trim());
        data.setLastName(createUserDTO.getLastName().trim());
        data.setPassword(bCryptPasswordEncoder.encode(createUserDTO.getPassword().trim()));
        data.getRoles().add(Roles.USER);

        return data;
    }
    private void checkRegisterDTO(CreateUserDTO createUserDTO) {
        if (!createUserDTO.getPassword().equals(createUserDTO.getConfirmPassword())) {
            throw new InvalidDataException("Password not equals");
        }
        if (!(storage.findByEmail(createUserDTO.getEmail()) == null)) {
            throw new AlreadyExistsException("User already exist");
        }
    }

    public void checkAuth(UserDTO userDTO){
        Users user = storage.findByEmail(userDTO.getEmail());
        if(user == null){
            throw new InvalidDataException(String.format("user with email %s is not exist",userDTO.getEmail()));
        }
        if(!bCryptPasswordEncoder.matches(userDTO.getPassword(),user.getPassword())){
            throw new InvalidDataException("password is not correct");
        }
    }
}
