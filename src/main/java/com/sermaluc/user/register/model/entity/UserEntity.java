package com.sermaluc.user.register.model.entity;

import com.sermaluc.user.register.model.Status;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @Column(name = "created", nullable = false, unique = true)
    private Date created;

    @Column(name = "modified", nullable = false, unique = true)
    private Date modified;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    private List<PhoneEntity> phones;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
