package com.jtc.service.Oauthloginservice.oauth2Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "person_entity")
public class PersonEntity {

    @Id
    private String username;
    private String email;
    private String roles;
    private String passwords;

}
