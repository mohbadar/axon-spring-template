package com.unite.axon_spring.iam.model;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
public class BaseEntity {

    @Id
    public String id;

    public BaseEntity() {
        if (id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}
