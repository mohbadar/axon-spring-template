package com.unite.axon_spring.library.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterLibraryCommand {

    private Integer libraryId;
    private String name;
}
