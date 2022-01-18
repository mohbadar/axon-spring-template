package com.unite.axon_spring.library.repository;

import com.unite.axon_spring.library.dto.BookDTO;
import com.unite.axon_spring.library.entity.BookEntity;
import com.unite.axon_spring.library.event.BookCreatedEvent;
import com.unite.axon_spring.library.query.GetBooksQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookRepositoryService {

    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventHandler
    public void addBook(BookCreatedEvent event) throws Exception
    {
        BookEntity book = new BookEntity();
        book.setIsbn(event.getIsbn());
        book.setLibraryId(event.getLibraryId());
        book.setTitle(event.getTitle());
        bookRepository.save(book);
    }

    @QueryHandler
    public List<BookDTO> getBooks(GetBooksQuery query)
    {
        return bookRepository.findByLibraryId(query.getLibraryId()).stream().map(toBook()).collect(Collectors.toList());
    }

    private Function<BookEntity, BookDTO> toBook() {
        return e -> {
            BookDTO book = new BookDTO();
            book.setIsbn(e.getIsbn());
            book.setTitle(e.getTitle());
            return book;
        };
    }
}
