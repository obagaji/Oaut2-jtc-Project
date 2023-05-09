package com.jtc.service.Oauthloginservice.oauth2Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

    private String username;
    private String userPassword;
}
