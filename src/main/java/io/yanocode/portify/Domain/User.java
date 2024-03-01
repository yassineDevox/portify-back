package io.yanocode.portify.Domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name="Users")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {
    @Id
    @UuidGenerator
    @Column(name="id", unique = true,updatable = false)
    private String id;
    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    @Column(unique = true)
    private String email;
    private String password;
    private String githubUrl;
    private String linkedinUrl;
    private String photoUrl;
}
