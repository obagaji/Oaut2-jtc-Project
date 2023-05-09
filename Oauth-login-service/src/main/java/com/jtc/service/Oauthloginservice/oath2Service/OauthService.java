package com.jtc.service.Oauthloginservice.oath2Service;

import com.jtc.service.Oauthloginservice.exceptionClass.NoSuchNameException;
import com.jtc.service.Oauthloginservice.oath2Repository.OauthRepository;
import com.jtc.service.Oauthloginservice.oauth2Dto.LoginDto;
import com.jtc.service.Oauthloginservice.oauth2Entity.JwtService;
import com.jtc.service.Oauthloginservice.oauth2Entity.PersonEntity;
import com.jtc.service.Oauthloginservice.oauth2Entity.PersonUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class OauthService {

    @Autowired
    private  OauthRepository oauthRepository;
    @Autowired
    private JwtService jwtService;
    public PersonEntity userRegister(PersonEntity personEntity)
    {
         PersonEntity person = oauthRepository.save(personEntity);
    //   String token =  jwtService.generateToken(new PersonUser(person));
       return person;
    }
    public String homeLogin(LoginDto loginDto)throws NoSuchNameException
    {
        PersonEntity personEntity = oauthRepository.findById(loginDto.getUsername()).orElseThrow( NoSuchNameException:: new );
        String token = jwtService.generateToken(new PersonUser(personEntity));
        return token;
    }

    public List<PersonEntity> getAllInformation()
    {

        List< PersonEntity> findAllPersons = oauthRepository.findAll();

        return findAllPersons;
    }
}
