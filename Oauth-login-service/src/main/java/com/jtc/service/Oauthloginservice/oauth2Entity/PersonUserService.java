package com.jtc.service.Oauthloginservice.oauth2Entity;

import com.jtc.service.Oauthloginservice.exceptionClass.NoSuchNameException;
import com.jtc.service.Oauthloginservice.oath2Repository.OauthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonUserService implements UserDetailsService {
    @Autowired
    private OauthRepository oauthRepository;
/*    @Autowired
    private PersonEntity personEntity;*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        PersonEntity personEntity = oauthRepository.findById(username).orElseThrow();

        if(personEntity == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return new PersonUser(personEntity);
    }
}
