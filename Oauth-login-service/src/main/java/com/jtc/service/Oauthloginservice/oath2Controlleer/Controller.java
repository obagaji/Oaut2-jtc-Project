package com.jtc.service.Oauthloginservice.oath2Controlleer;


import com.jtc.service.Oauthloginservice.exceptionClass.NoSuchNameException;
import com.jtc.service.Oauthloginservice.oath2Service.OauthService;
import com.jtc.service.Oauthloginservice.oauth2Dto.LoginDto;
import com.jtc.service.Oauthloginservice.oauth2Entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/registration")
public class Controller {

    @Autowired
    private OauthService oauthService;

    @PostMapping ("/register")
    public ResponseEntity<PersonEntity> getRegisteredUser(@RequestBody PersonEntity personEntity)
    {
        PersonEntity token = oauthService.userRegister(personEntity);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("register").build(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
    }
    @PostMapping("/login")
    public ResponseEntity<String> requestLogin(@RequestBody LoginDto loginDto) throws NoSuchNameException {
        String token = oauthService.homeLogin(loginDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("login").build(token);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setLocation(uri);
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(httpHeaders).build();
    }

}
