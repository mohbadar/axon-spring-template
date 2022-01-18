package com.unite.axon_spring.library.event;

import lombok.Data;

@Data
public class LibraryCreatedEvent {
    private final Integer libraryId;
    private final String name;
}
