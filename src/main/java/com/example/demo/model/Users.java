package com.example.demo.model;

import com.example.demo.model.enums.Roles;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Users extends AbstractModel{
    private String email;
    private String login;
    private String password;
    private String name;
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    @JsonManagedReference
    private List<Application> application = new ArrayList<>();
    @ElementCollection(
            targetClass = Roles.class,
            fetch = FetchType.EAGER
    )
    @CollectionTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles = new HashSet<>();
    @OneToOne(
            cascade = CascadeType.REFRESH,
            fetch = FetchType.LAZY)
    private Image avatar;
}
