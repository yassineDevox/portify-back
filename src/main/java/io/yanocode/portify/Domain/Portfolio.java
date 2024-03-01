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
@Table(name="Portfolios")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Portfolio {
    @Id
    @UuidGenerator
    @Column(unique = true, updatable = false)
    private String id;
    private String profile;
    @Column(columnDefinition = "TEXT")
    private String bio;
}
