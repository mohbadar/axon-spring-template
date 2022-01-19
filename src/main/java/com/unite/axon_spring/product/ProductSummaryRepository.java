package com.unite.axon_spring.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSummaryRepository extends CrudRepository<ProductSummary, String> {
}
