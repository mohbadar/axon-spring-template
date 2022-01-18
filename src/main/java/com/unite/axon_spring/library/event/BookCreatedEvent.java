package com.unite.axon_spring.library.event;

import lombok.Data;

@Data
public class BookCreatedEvent {
    private final Integer libraryId;
    private final String isbn;
    private final String title;
}

