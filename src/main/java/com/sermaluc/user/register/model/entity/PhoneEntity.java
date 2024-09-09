package com.sermaluc.user.register.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "phones")
public class PhoneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.EAGER)
    private UserEntity user;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "city_code", nullable = false, unique = true)
    private String cityCode;

    @Column(name = "contry_code", nullable = false, unique = true)
    private String contryCode;
}
