package com.unite.axon_spring.product;

import com.unite.axon_spring.product.command.AddProductCommand;
import com.unite.axon_spring.product.command.DeleteProductCommand;
import com.unite.axon_spring.product.command.UpdateProductCommand;
import com.unite.axon_spring.product.event.ProductAddedEvent;
import com.unite.axon_spring.product.event.ProductDeletedEvent;
import com.unite.axon_spring.product.event.ProductUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;


@Aggregate
public class Product {
    @AggregateIdentifier
    private String id;
    private Double price;
    private Integer stock;
    private String description;
    private boolean deleted;

    public Product(){}

    @CommandHandler
    public Product(AddProductCommand cmd)
    {
        apply( new ProductAddedEvent(
                        cmd.getId(),
                        cmd.getPrice(),
                        cmd.getStock(),
                        cmd.getDescription()
                )
        );
    }

    @CommandHandler
    public void updateProduct(UpdateProductCommand cmd)
    {
        System.out.println(cmd.getDescription());
        apply(new ProductUpdatedEvent(
                cmd.getId(),
                cmd.getPrice(),
                cmd.getStock(),
                cmd.getDescription()
        ));
    }

    @CommandHandler
    public void deleteProduct(DeleteProductCommand cmd)
    {
        apply(
          new ProductDeletedEvent(cmd.getId())
        );
    }

    @EventSourcingHandler
    private void on(ProductAddedEvent evt)
    {
        id = evt.getId();
        price = evt.getPrice();
        stock = evt.getStock();
        description = evt.getDescription();
        deleted=false;
    }

    @EventSourcingHandler
    private void on(ProductUpdatedEvent evt)
    {
        price = evt.getPrice();
        stock = evt.getStock();
        description = evt.getDescription();
    }

    @EventSourcingHandler
    private void on(ProductDeletedEvent evt)
    {
        this.deleted=true;
    }


}
