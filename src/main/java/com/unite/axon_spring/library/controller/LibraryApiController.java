package com.unite.axon_spring.library.controller;

import com.unite.axon_spring.library.aggregate.Library;
import com.unite.axon_spring.library.command.RegisterBookCommand;
import com.unite.axon_spring.library.command.RegisterLibraryCommand;
import com.unite.axon_spring.library.dto.BookDTO;
import com.unite.axon_spring.library.dto.LibraryDTO;
import com.unite.axon_spring.library.query.GetBooksQuery;
import com.unite.axon_spring.library.query.GetLibraryQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/api/library")
public class LibraryApiController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    public LibraryApiController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping(path = "/register")
    public HttpStatus addLibrary(@RequestBody LibraryDTO libraryDTO)
    {
        commandGateway.send(new RegisterLibraryCommand(libraryDTO.getLibraryId(), libraryDTO.getName()));
        return HttpStatus.OK;
    }


    @GetMapping(path = "/get/{libraryId}")
    public Library getLibrary(@PathVariable(required = true) Integer libraryId) throws Exception
    {
        CompletableFuture<Library> future = queryGateway.query(new GetLibraryQuery(libraryId), Library.class);
        return future.get();
    }

    @PostMapping(path = "/books/{libraryId}/register")
    public HttpStatus addBook(@PathVariable(required = true) Integer libraryId, @RequestBody BookDTO dto)
    {
        commandGateway.send(new RegisterBookCommand(libraryId, dto.getIsbn(), dto.getTitle()));
        return HttpStatus.OK;
    }

    @GetMapping(path = "/books/{libraryId}")
    public List<BookDTO> getLibraryBooks(@PathVariable(required = true) Integer libraryId) throws Exception{
        return queryGateway.query(new GetBooksQuery(libraryId), ResponseTypes.multipleInstancesOf(BookDTO.class)).get();
    }
}
