package com.unite.axon_spring.library.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class RegisterBookCommand {
    @TargetAggregateIdentifier
    public final Integer libraryId;
    public final String isbn;
    public final String title;
}
