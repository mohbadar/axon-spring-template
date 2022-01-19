package com.unite.axon_spring.product;

import com.unite.axon_spring.product.event.ProductAddedEvent;
import com.unite.axon_spring.product.event.ProductDeletedEvent;
import com.unite.axon_spring.product.event.ProductUpdatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductProjector {


    private final ProductSummaryRepository productSummaryRepository;

    @Autowired
    public ProductProjector(ProductSummaryRepository productSummaryRepository) {
        this.productSummaryRepository = productSummaryRepository;
    }

    @EventHandler
    public void addProduct(ProductAddedEvent event)
    {
        ProductSummary productSummary = new ProductSummary();
        productSummary.setId(event.getId());
        productSummary.setDescription(event.getDescription());
        productSummary.setPrice(event.getPrice());
        productSummary.setStock(event.getStock());
        productSummaryRepository.save(productSummary);
    }

    @EventHandler
    public void updateProduct(ProductUpdatedEvent event)
    {
        ProductSummary productSummary = productSummaryRepository.findById(event.getId()).orElse(null);

        if(productSummary == null)
            throw new RuntimeException("Null ProductException");

        productSummary.setDescription(event.getDescription());
        productSummary.setPrice(event.getPrice());
        productSummary.setStock(event.getStock());
        productSummaryRepository.save(productSummary);
    }


    @EventHandler
    public void deleteProduct(ProductDeletedEvent event)
    {
        ProductSummary productSummary = productSummaryRepository.findById(event.getId()).orElse(null);

        if(productSummary == null)
            throw new RuntimeException("Null ProductException");
        productSummary.setDeleted(true);
        productSummaryRepository.save(productSummary);
    }
}
