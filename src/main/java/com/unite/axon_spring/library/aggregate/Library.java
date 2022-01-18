package com.unite.axon_spring.library.aggregate;

import com.unite.axon_spring.library.command.RegisterBookCommand;
import com.unite.axon_spring.library.command.RegisterLibraryCommand;
import com.unite.axon_spring.library.event.BookCreatedEvent;
import com.unite.axon_spring.library.event.LibraryCreatedEvent;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Aggregate
@Getter
@Setter
public class Library {

    @AggregateIdentifier
    private Integer libraryId;
    private String name;
    private List<String> isbnBooks;

    protected Library(){
        //for Axon Instantiation
    }


    @CommandHandler
    public Library(RegisterLibraryCommand registerLibraryCommand)
    {
        Assert.notNull( registerLibraryCommand.getLibraryId(), "ID is required");
        Assert.notNull(registerLibraryCommand.getName(), "Name should not be null");

//        System.out.println("LibraryCommand: "+ registerLibraryCommand.getName());
        AggregateLifecycle.apply(new LibraryCreatedEvent(registerLibraryCommand.getLibraryId(), registerLibraryCommand.getName()));
    }

    @CommandHandler
    public void addBook(RegisterBookCommand registerBookCommand)
    {
        Assert.notNull(registerBookCommand.getLibraryId(), "Id is required");
        Assert.notNull(registerBookCommand.getIsbn(), "ISBN is required");
        Assert.notNull(registerBookCommand.getTitle(), "Title is required");

        AggregateLifecycle.apply(new BookCreatedEvent(registerBookCommand.libraryId, registerBookCommand.isbn, registerBookCommand.title));
    }

    @EventSourcingHandler
    private void addBook(BookCreatedEvent event){
        isbnBooks.add(event.getIsbn());
    }

    @EventSourcingHandler
    private void handleCreatedEvent(LibraryCreatedEvent event)
    {
        libraryId = event.getLibraryId();
        name = event.getName();
        isbnBooks = new ArrayList<>();
    }
}
