package com.sermaluc.user.register.model.dto;

import com.sermaluc.user.register.model.Status;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String password;
    private Date created;
    private Date modified;
    private Status status;
    private List<PhoneDTO> phones;
}
