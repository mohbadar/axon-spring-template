package com.unite.axon_spring.library.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BookEntity {
    @Id
    private String isbn;
    @Column
    private Integer libraryId;
    @Column
    private String title;
}
