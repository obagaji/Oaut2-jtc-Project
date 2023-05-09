package com.jtc.service.Oauthloginservice.oath2Repository;


import com.jtc.service.Oauthloginservice.oauth2Entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthRepository extends JpaRepository<PersonEntity,String> {
}
