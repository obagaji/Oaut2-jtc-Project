package com.jtc.service.Oauthloginservice.oath2Controlleer;


import com.jtc.service.Oauthloginservice.oath2Service.OauthService;
import com.jtc.service.Oauthloginservice.oauth2Entity.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping()
public class OauthController {

    @Autowired
    private OauthService oauthService;

    @GetMapping("/allInformation")
    public ResponseEntity<List<PersonEntity>> getAllInformation()
    {
        List<PersonEntity> personList = oauthService.getAllInformation();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/allInformation").build(personList);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<List<PersonEntity>>(personList, HttpStatus.OK);
    }
    @GetMapping()
    public String getAll()
    {

        return "You are welcome";
    }

}
