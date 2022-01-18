package com.unite.axon_spring.library.repository;

import com.unite.axon_spring.library.entity.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
    List<BookEntity> findByLibraryId(Integer libraryId);
}
