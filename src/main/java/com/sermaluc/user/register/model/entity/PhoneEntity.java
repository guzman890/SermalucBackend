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

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "city_code", nullable = false, unique = true)
    private String cityCode;

    @Column(name = "contry_code", nullable = false, unique = true)
    private String contryCode;
}
