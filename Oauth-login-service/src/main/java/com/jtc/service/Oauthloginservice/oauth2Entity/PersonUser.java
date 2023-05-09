package com.jtc.service.Oauthloginservice.oauth2Entity;
import com.jtc.service.Oauthloginservice.oath2Repository.OauthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
public class PersonUser implements UserDetails {

    @Autowired
    private  PersonEntity personEntity;

    private String username;
    private String password;
    private String email;

    private List<GrantedAuthority> roles;


    public PersonUser(PersonEntity personEntity)
    {
        this.password = personEntity.getPasswords();
        this.username = personEntity.getUsername();
        this.email = personEntity.getEmail();
        this.roles =  List.of(personEntity.getRoles()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return roles;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
