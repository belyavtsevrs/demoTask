package com.example.demo.service;

import com.example.demo.DTO.ApplicationDTO;
import com.example.demo.model.Application;
import com.example.demo.model.Users;
import com.example.demo.repository.ApplicationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.common.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
public class ApplicationService extends AbstractService<Application, ApplicationRepository> {
    private final UserRepository userRepository;
    public ApplicationService(ApplicationRepository storage, UserRepository userRepository) {
        super(storage);
        this.userRepository = userRepository;
    }

    public Application create(ApplicationDTO application,Principal principal) {
            Application data = new Application();
            data.setUser(getUserByPrincipal(principal));
            data.setTitle(application.getTitle());
            data.setCount(application.getCount());
            data.setAddress(application.getAddress());
            data.setPhoneNumber(application.getPhoneNumber());
            log.info("appEntity = {}",data);
            return storage.save(data);
    }

    @Override
    public List<Application> findAll() {
        return super.findAll();
    }

    @Override
    public Application findById(Long id) {
        return super.findById(id);
    }
    private Users getUserByPrincipal(Principal principal){
        return userRepository.findByEmail(principal.getName());
    }
}
