package com.sermaluc.user.register.model.request;

import com.sermaluc.user.register.model.dto.PhoneDTO;
import java.util.List;
import lombok.Data;


@Data
public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private List<PhoneDTO> phones;
}
