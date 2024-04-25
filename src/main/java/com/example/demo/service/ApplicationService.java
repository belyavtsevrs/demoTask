package com.example.demo.service;

import com.example.demo.DTO.ApplicationDTO;
import com.example.demo.DTO.CreateApplicationDTO;
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

    public Application create(CreateApplicationDTO application, Principal principal) {
            return storage.save(toEntity(application,principal));
    }
    public List<ApplicationDTO> allDTO(){
        return storage.findAll().stream().map(x->toApplicationDTO(x)).toList();
    }
    @Override
    public List<Application> findAll() {
        return super.findAll();
    }
    @Override
    public Application findById(Long id) {
        return super.findById(id);
    }
    private Application toEntity(CreateApplicationDTO createApplicationDTO,Principal principal){
        Application data = new Application();
        data.setUser(getUserByPrincipal(principal));
        data.setTitle(createApplicationDTO.getTitle());
        data.setCount(createApplicationDTO.getCount());
        data.setAddress(createApplicationDTO.getAddress());
        data.setPhoneNumber(createApplicationDTO.getPhoneNumber());
        log.info("appEntity = {}",data);
        return data;
    }

    private ApplicationDTO toApplicationDTO(Application application){
        ApplicationDTO applicationDTO = new ApplicationDTO();
        applicationDTO.setTitle(application.getTitle());
        applicationDTO.setCount(application.getCount());
        applicationDTO.setPhoneNumber(application.getPhoneNumber());
        applicationDTO.setAddress(application.getAddress());
        applicationDTO.setCreator(application.getUser().getName()+" "+application.getUser().getLastName());
        return applicationDTO;
    }
    private Users getUserByPrincipal(Principal principal){
        return userRepository.findByEmail(principal.getName());
    }
}
