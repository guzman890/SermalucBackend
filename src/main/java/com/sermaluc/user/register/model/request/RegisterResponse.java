package com.sermaluc.user.register.model.request;

import com.sermaluc.user.register.model.Status;
import java.util.Date;
import lombok.Data;

@Data
public class RegisterResponse {
    private Long id;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private Status isactive;
}
