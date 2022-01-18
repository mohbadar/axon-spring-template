package com.unite.axon_spring.library.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBooksQuery {
    private Integer libraryId;
}
