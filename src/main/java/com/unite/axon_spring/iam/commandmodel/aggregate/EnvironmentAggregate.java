package com.unite.axon_spring.iam.commandmodel.aggregate;

import com.unite.axon_spring.iam.coreapi.*;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@NoArgsConstructor
public class EnvironmentAggregate {
    @AggregateIdentifier
    private String id;
    private String slug;
    private String name;
    private String description;
    private String secretKey;
    private boolean active;


    @CommandHandler
    public EnvironmentAggregate(CreateEnvironmentCommand cmd){
        System.out.println(cmd.toString());
        apply(new EnvironmentCreatedEvent(
                cmd.getId(),
                cmd.getSlug(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.getSecretKey(),
                cmd.getActive()
        ));
    }

    @CommandHandler
    public void handle(UpdateEnvironmentCommand cmd)
    {
        apply(new EnvironmentUpdatedEvent(
                cmd.getId(),
                cmd.getSlug(),
                cmd.getName(),
                cmd.getDescription(),
                cmd.getSecretKey(),
                cmd.getActive()
        ));
    }


    @CommandHandler
    public void handle(DeactivateEnvironmentCommand cmd)
    {
        apply(new EnvironmentDeactivatedEvent(
                cmd.getId()
        ));
    }

    @EventSourcingHandler
    private void on(EnvironmentCreatedEvent event)
    {
        this.id=event.getId();
        this.slug=event.getSlug();
        this.name=event.getName();
        this.description=event.getSecretKey();
        this.secretKey=event.getSecretKey();
        this.active=event.getActive();
    }

    @EventSourcingHandler
    private void on(EnvironmentUpdatedEvent event)
    {
        this.slug=event.getSlug();
        this.name=event.getName();
        this.description=event.getSecretKey();
        this.secretKey=event.getSecretKey();
        this.active=event.getActive();
    }

    @EventSourcingHandler
    private void on(EnvironmentDeactivatedEvent event)
    {
        this.active=false;
    }
}
