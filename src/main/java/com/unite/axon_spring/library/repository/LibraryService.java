package com.unite.axon_spring.library.repository;

import com.unite.axon_spring.library.aggregate.Library;
import com.unite.axon_spring.library.dto.LibraryDTO;
import com.unite.axon_spring.library.query.GetLibraryQuery;
import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class LibraryService {

    private final Repository<Library> libraryRepository;

    @Autowired
    public LibraryService(Repository<Library> libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @QueryHandler
    public Library getLibrary(GetLibraryQuery query) throws InterruptedException, ExecutionException
    {
        CompletableFuture<Library> future = new CompletableFuture<Library>();
        libraryRepository.load("" + query.getLibraryId()).execute(future::complete);
        return future.get();
    }
}
